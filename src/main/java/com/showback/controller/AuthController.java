package com.showback.controller;

import com.showback.dto.SocialLoginDTO;
import com.showback.dto.UserDTO;
import com.showback.model.User;
import com.showback.security.TokenProvider;
import com.showback.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final TokenProvider tokenProvider;


    // kakao
    @GetMapping("/oauth/kakao")
    public ResponseEntity<?> kakaoLogin(HttpServletRequest request){

        String code = request.getParameter("code");
//        System.out.println("------------------------------------------code = " + code);
//        String kakaoAccessToken = authService.getKakaoAccessToken(code);
        SocialLoginDTO socialLoginDTO = authService.getKakaoAccessToken(code);
//        String token = socialLoginDTO.getAccess_token();
//        System.out.println("-token = " + token);
        String socialUserIdFromProvider = authService.socialLoginInfo(socialLoginDTO);
//        System.out.println("-------------------socialUserIdFromProvider = " + socialUserIdFromProvider);

//        System.out.println("kakdoAccessToken = " + kakaoAccessToken);
//        return authService.kakaoLogin(kakdoAccessToken);
        if(socialLoginDTO != null) {
//            return ResponseEntity.ok().body(Map.of("kakaoAccessToken", kakaoAccessToken));
//            return ResponseEntity.ok().body(token);
            String ipAddress =  request.getRemoteAddr();
            String userAgent = request.getHeader("User-Agent");
            User userEntity = authService.getByCredentials(socialUserIdFromProvider);
//
            final String token = tokenProvider.create(userEntity, ipAddress, userAgent);
            System.out.println("----------------------token = " + token);
            final UserDTO responseUserDTO = UserDTO.builder()
                    .username(socialUserIdFromProvider)
                    .token(token)
                    .build();
            return ResponseEntity.ok().body(responseUserDTO);

        } else {
            return ResponseEntity.badRequest().body("Failed to get kakaoAccessToken");
        }
    }

//    @PostMapping("/user/oauth/kakao/logout")
//    public ResponseEntity<?> kakaoLogin(String accessToken) {
//
//
//        return null;
//    }



}
