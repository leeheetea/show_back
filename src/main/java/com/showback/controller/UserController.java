package com.showback.controller;

import com.showback.DTO.UserDTO;
import com.showback.model.Password;
import com.showback.model.User;
import com.showback.model.UserAuth;
import com.showback.security.TokenProvider;
import com.showback.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenProvider tokenProvider;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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


}
