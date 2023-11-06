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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TokenProvider tokenProvider;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // join
    @PostMapping
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO){

//        User userEntity = new User();
//        userEntity.setUsername(userDTO.getUsername());
//
//        Password passwordEntity = new Password();
//        passwordEntity.setUser(userEntity);
//        passwordEntity.setUserPassword(passwordEncoder.encode(userDTO.getPassword()));
//
//        UserAuth userAuthEntity = new UserAuth();
//        userAuthEntity.setUser(userEntity);
//        userAuthEntity.setAuthName(userDTO.getName());
//        userAuthEntity.setAuthEmail(userDTO.getEmail());
//        userAuthEntity.setAuthPhone(userDTO.getPhone());
//        userAuthEntity.setSmsChoice(userDTO.isSmscheck());
//        userAuthEntity.setValidatePeriod(userDTO.getIsRadioChecked());
//
//        userService.register(userEntity, passwordEntity, userAuthEntity);
//
//        UserDTO responseUserDTO = new UserDTO();
//        responseUserDTO.setName(userDTO.getName());
//
//        return ResponseEntity.ok().body(responseUserDTO);
        UserDTO responseUserDTO = userService.register(userDTO);
        return ResponseEntity.ok().body(responseUserDTO);
    }

    // login
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(HttpServletRequest request,@RequestBody UserDTO userDTO) {
        String ipAddress = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        User userEntity = userService.getByCredentials(userDTO.getUsername(), userDTO.getPassword(), passwordEncoder, ipAddress, userAgent);

        if(userEntity != null) {
            final String token = tokenProvider.create(userEntity, ipAddress, userAgent);
            final UserDTO responseUserDTO = UserDTO.builder()
                    .username(userDTO.getUsername())
                    .token(token)
                    .build();
            return ResponseEntity.ok().body(responseUserDTO);
        } else {
            int lastFailureCount = userService.getLastLoginFailureCount(userDTO.getUsername());
            return ResponseEntity.badRequest().body(lastFailureCount);
        }
    }

    // find username
    @PostMapping("/username/retrieve")
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

    // find password
    @PostMapping("/password/request")
    public ResponseEntity<?> requestPassword(@RequestBody UserDTO userDTO){
       Password resetPassword = userService.requestPassword(userDTO.getUsername(), userDTO.getName(), userDTO.getEmail());

       if(resetPassword != null) {
           return ResponseEntity.ok().body("Password found");
       } else {
           return ResponseEntity.badRequest().body("User not found");
       }
    }

    // updatePasswordByUsername
    @PostMapping("/password/retrieve")
    public ResponseEntity<?> retrievePassword(@RequestBody UserDTO userDTO) {
        Password updatePassword = userService.updatePasswordByUsername(userDTO.getUsername(), userDTO.getPassword(), passwordEncoder);

        if(updatePassword != null) {
            return ResponseEntity.ok().body("password updated");
        } else {
            return ResponseEntity.badRequest().body("password updated fail");
        }
    }

    // resetPassword
    @PostMapping("/password/reset")
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

    // email update
    @PostMapping("/email/update")
    public ResponseEntity<?> updateEmail(HttpServletRequest request, @RequestBody UserDTO userDTO){
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String userIdStr = tokenProvider.validateAndGetUserId(token);
        Long userId = Long.parseLong(userIdStr);

        if(userId != null) {
            userService.updateEmailByUserId(userId, userDTO.getEmail());
            return ResponseEntity.ok().body("email updated");
        }

        return ResponseEntity.badRequest().body("User not found");
    }

    // password auth
    @PostMapping("/password/authentication")
    public ResponseEntity<?> passwordAuthentication(HttpServletRequest request, @RequestBody UserDTO userDTO) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String userIdStr = tokenProvider.validateAndGetUserId(token);
        Long userId = Long.parseLong(userIdStr);

        if(userId != null) {
            Password vPassword = userService.verifyPasswordBeforeUpdate(userId, userDTO.getPassword(), passwordEncoder);
            if (vPassword != null) {
                return ResponseEntity.ok().body("Verified");
            } else {
                return ResponseEntity.badRequest().body("Incorrect Password");
            }
        }
        return  ResponseEntity.badRequest().body("User not found");
    }

    //
    @PostMapping("/userinfo/authentication")
    public ResponseEntity<?> getUser(HttpServletRequest request, @RequestBody UserDTO userDTO) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String userIdStr = tokenProvider.validateAndGetUserId(token);
        Long userId = Long.parseLong(userIdStr);

        if(userId != null) {
            UserDTO responseDTO = userService.verifyPasswordBeforeGetUser(userId, userDTO.getPassword(), passwordEncoder);
            if (responseDTO != null) {
                return ResponseEntity.ok().body(responseDTO);
            } else {
                return ResponseEntity.badRequest().body("Incorrect Password");
            }
        }
        return  ResponseEntity.badRequest().body("User not found");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, @RequestBody UserDTO userDTO) {
        String logoutUsername = userService.logout(userDTO.getToken());
        if(logoutUsername != null) {
            return ResponseEntity.ok().body(logoutUsername);
        } else {
            return ResponseEntity.badRequest().body("Error");
        }
    }

}
