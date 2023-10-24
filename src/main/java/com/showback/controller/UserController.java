package com.showback.controller;

import com.showback.dto.UserDTO;
import com.showback.model.Password;
import com.showback.model.User;
import com.showback.model.UserAuth;
import com.showback.security.TokenProvider;
import com.showback.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TokenProvider tokenProvider;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // 회원가입
    @PostMapping("/user")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO){

        User userEntity = new User();
        userEntity.setUsername(userDTO.getUsername());

        Password passwordEntity = new Password();
        passwordEntity.setUser(userEntity);
        passwordEntity.setUserPassword(passwordEncoder.encode(userDTO.getPassword()));

        UserAuth userAuthEntity = new UserAuth();
        userAuthEntity.setUser(userEntity);
        userAuthEntity.setAuthName(userDTO.getName());
        userAuthEntity.setAuthEmail(userDTO.getEmail());
        userAuthEntity.setAuthPhone(userDTO.getPhone());
        userAuthEntity.setSmsChoice(userDTO.isSmscheck());
        userAuthEntity.setValidatePeriod(userDTO.getIsRadioChecked());

        userService.register(userEntity, passwordEntity, userAuthEntity);

        UserDTO responseUserDTO = new UserDTO();
        responseUserDTO.setName(userDTO.getName());

//        return new ResponseEntity<>("registered", HttpStatus.CREATED);
        return ResponseEntity.ok().body(responseUserDTO);
    }

    // 로그인
    @PostMapping("/user/login")
    public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO) {
        User userEntity = userService.getByCredentials(userDTO.getUsername(), userDTO.getPassword(), passwordEncoder);

        if(userEntity != null) {
            final String token = tokenProvider.create(userEntity);
            final UserDTO responseUserDTO = UserDTO.builder()
                    .username(userDTO.getUsername())
                    .token(token)
                    .build();
            return ResponseEntity.ok().body(responseUserDTO);
        } else {
            // temp
            UserDTO tempDTO = UserDTO.builder().build();
            return ResponseEntity.badRequest().body(tempDTO);
        }
    }

    // 아이디 찾기
    @PostMapping("/user/username/retrieve")
    public ResponseEntity<?> retrieveUsername(@RequestBody UserDTO userDTO) {
        UserDTO responseUserDTO = userService.retrieveUsername(userDTO.getName(), userDTO.getEmail());

        if(responseUserDTO != null) {
            return ResponseEntity.ok().body(responseUserDTO);
        }else {
            // temp
            UserDTO tempDTO = UserDTO.builder().build();
            return ResponseEntity.badRequest().body(tempDTO);
        }
    }

    // 비밀번호 재설정
//    @PostMapping("/user/password/retrieve")
//    public ResponseEntity<?> retrievePassword(@RequestBody UserDTO userDTO){
//       Password resetPassword = userService.retrievePassword(userDTO.getUsername(), userDTO.getName(), userDTO.getEmail());
//
//       if(resetPassword != null) {
//
//       }
//        return  null;
//    }

    @PostMapping("/user/password/retrieve")
    public ResponseEntity<?> retrievePassword(@RequestBody UserDTO userDTO) {
        userService.updatePasswordByUsername(userDTO.getUsername(), userDTO.getPassword(), passwordEncoder);

        return ResponseEntity.ok().body("password updated");
    }

    @PostMapping("/user/password/reset")
    public ResponseEntity<?> resetPassword(
            HttpServletRequest request,
            @RequestBody UserDTO userDTO) {

        // 헤더에서 토큰 추출
        String token = request.getHeader("Authorization").replace("Bearer ", "");

        // 토큰에서 userId 추출
        String userIdStr = tokenProvider.validateAndGetUserId(token);
        Long userId = Long.parseLong(userIdStr);  // String에서 Long으로 변환

        // userService를 확장하여 userId를 기반으로 User 엔티티를 반환하는 메서드를 추가합니다.
//        User userEntity = userService.findByUserId(userId);

        if(userId != null) {
            userService.updatePasswordByUserId(userId, userDTO.getPassword(), passwordEncoder);
            return ResponseEntity.ok().body("password updated");
        }

        return ResponseEntity.badRequest().body("User not found");
    }


}
