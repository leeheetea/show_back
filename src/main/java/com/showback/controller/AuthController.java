package com.showback.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.showback.dto.SocialLoginDTO;
import com.showback.dto.UserDTO;
import com.showback.model.SocialLogin;
import com.showback.model.User;
import com.showback.security.TokenProvider;
import com.showback.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

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

        String ipAddress = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");

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
        if(socialUserIdFromProvider != null) {
//            return ResponseEntity.ok().body(Map.of("kakaoAccessToken", kakaoAccessToken));
//            return ResponseEntity.ok().body(token);
            //231103
//            String ipAddress =  request.getRemoteAddr();
//            String userAgent = request.getHeader("User-Agent");
//            User userEntity = authService.getByCredentials(socialUserIdFromProvider);
////
//            final String token = tokenProvider.create(userEntity, ipAddress, userAgent);
//            System.out.println("----------------------token = " + token);
//            final UserDTO responseUserDTO = UserDTO.builder()
//                    .username(socialUserIdFromProvider)
//                    .token(token)
//                    .build();

            if (!socialUserIdFromProvider.contains("@")) {
                // socialUserIdFromProvider = username
                User userEntity = authService.getByCredentials(socialUserIdFromProvider, ipAddress, userAgent);
                final String token = tokenProvider.create(userEntity, ipAddress, userAgent);
                final UserDTO responseUserDTO = UserDTO.builder()
                        .username(socialUserIdFromProvider)
                        .token(token)
                        .build();
                return ResponseEntity.ok().body(responseUserDTO);
            }

            UserDTO responseUserDTO = UserDTO.builder()
                    .email(socialUserIdFromProvider)
                    .build();
            return ResponseEntity.ok().body(responseUserDTO);

        } else {
            return ResponseEntity.badRequest().body("Failed to get kakaoAccessToken");
        }
    }

    // access token expired logout
    @PostMapping("/oauth/kakao/logout")
    public ResponseEntity<?> kakaoLogout(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String userIdStr = tokenProvider.validateAndGetUserId(token);
        Long userId = Long.parseLong(userIdStr);

        if(userId != null) {
            SocialLogin socialLogin = authService.socialLogout(userId);
            String accessToken = socialLogin.getAccessToken();
            System.out.println("-accessToken = " + accessToken);

            // 카카오 로그아웃 API 호출
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/x-www-form-urlencoded");
            headers.add("Authorization", "Bearer " + accessToken);
            HttpEntity<?> httpEntity = new HttpEntity<>(headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange("https://kapi.kakao.com/v1/user/logout", HttpMethod.POST, httpEntity, String.class);

            if(response.getStatusCode() != HttpStatus.OK) {
                return ResponseEntity.badRequest().body("Failed to log out from Kakao");
            }

            return ResponseEntity.ok().body("Logged out successfully");

        }
        return null;
    }

    // kakao true logout
    @GetMapping("/oauth/kakao/logout")
    public ResponseEntity<?> kakaoLogoutWithAccount() {
        URI kakaoLogoutUri = authService.initiateKakaoLogout();
        return ResponseEntity.status(HttpStatus.FOUND).location(kakaoLogoutUri).build();
    }


    ////////////////////////////////////////////////////////////////////////////////////////


    @PostMapping("/oauth/naver")
    public ResponseEntity<?> naverLogin(@RequestBody SocialLoginDTO socialLoginDTO, HttpServletRequest request) throws JsonProcessingException {
        String ipAddress = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        SocialLoginDTO getAccessToken = authService.getNaverAccessToken(socialLoginDTO.getCode(), socialLoginDTO.getState());

        if(getAccessToken != null) {
            String userEmail = authService.getNaverUserProfile(getAccessToken.getAccess_token());
            if(userEmail != null) {
                String usernameOrEmail = authService.naverLogin(userEmail, getAccessToken);

                if (!usernameOrEmail.contains("@")) {
                    // socialUserIdFromProvider = username
                    User userEntity = authService.getByCredentials(usernameOrEmail, ipAddress, userAgent);
                    final String token = tokenProvider.create(userEntity, ipAddress, userAgent);
                    final UserDTO responseUserDTO = UserDTO.builder()
                            .username(usernameOrEmail)
                            .token(token)
                            .build();
                    return ResponseEntity.ok().body(responseUserDTO);
                }

                UserDTO responseUserDTO = UserDTO.builder()
                        .email(usernameOrEmail)
                        .build();
                return ResponseEntity.ok().body(responseUserDTO);

            }
        }

        return null;

    }
}
