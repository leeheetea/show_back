package com.showback.controller;

import com.showback.dto.UserDTO;
import com.showback.model.LoginLog;
import com.showback.model.Password;
import com.showback.model.User;
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

    @PostMapping
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO){

        UserDTO responseUserDTO = userService.register(userDTO);
        return ResponseEntity.ok().body(responseUserDTO);
    }

    // login
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(HttpServletRequest request,@RequestBody UserDTO userDTO) {
        String ipAddress = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");

        User isExistingUser = userService.getByUsername(userDTO.getUsername());
        if(isExistingUser != null) {
            LoginLog lastLoginLog = userService.getLastLoginLog(isExistingUser);
            if(lastLoginLog != null && lastLoginLog.getAccountStatus()) {
                return ResponseEntity.badRequest().body("User already logged");
            }
        }

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
            return ResponseEntity.badRequest().body("User not found");
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

        String token = request.getHeader("Authorization").replace("Bearer ", "");

        String userIdStr = tokenProvider.validateAndGetUserId(token);
        Long userId = Long.parseLong(userIdStr);

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

<<<<<<< HEAD
    //유저 email 정보
    @GetMapping("/email")
    public String readUserEmail(HttpServletRequest request) {
=======
    // getUserData
    @PostMapping("/userinfo/authentication")
    public ResponseEntity<?> getUser(HttpServletRequest request, @RequestBody UserDTO userDTO) {
>>>>>>> e850a8de59c2c64fd2e344482c803febaa17c56e
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String userIdStr = tokenProvider.validateAndGetUserId(token);
        Long userId = Long.parseLong(userIdStr);

<<<<<<< HEAD
        String Email = userService.getByEmail(userId);

        return(Email);
=======
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

    @PostMapping("/name/request")
    public ResponseEntity<?> getName(HttpServletRequest request){
        String token = request.getHeader("Authorization").replace("Bearer ", "");

        String userIdStr = tokenProvider.validateAndGetUserId(token);
        Long userId = Long.parseLong(userIdStr);

        String name = userService.findName(userId);
        return  ResponseEntity.ok().body(name);
>>>>>>> e850a8de59c2c64fd2e344482c803febaa17c56e
    }


}
