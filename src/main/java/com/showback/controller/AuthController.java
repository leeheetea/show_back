package com.showback.controller;

import com.showback.dto.SocialLoginDTO;
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

    // kakao
    @GetMapping("/oauth/kakao")
    public ResponseEntity<?> kakaoLogin(HttpServletRequest request){

        String code = request.getParameter("code");
//        System.out.println("------------------------------------------code = " + code);
//        String kakaoAccessToken = authService.getKakaoAccessToken(code);
        SocialLoginDTO kakaoAccessToken = authService.getKakaoAccessToken(code);


//        System.out.println("kakdoAccessToken = " + kakaoAccessToken);
//        return authService.kakaoLogin(kakdoAccessToken);
        if(kakaoAccessToken != null) {
//            return ResponseEntity.ok().body(Map.of("kakaoAccessToken", kakaoAccessToken));
            return ResponseEntity.ok().body(kakaoAccessToken);
        } else {
            return ResponseEntity.badRequest().body("Failed to get kakaoAccessToken");
        }
    }
}
