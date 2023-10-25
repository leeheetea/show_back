package com.showback.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.showback.dto.SocialLoginDTO;
import com.showback.model.SocialLogin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;


@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    String KAKAO_CLINET_ID = "98fb1054fadbc801e5b9337e8492549d";
    String KAKAO_REDIRECT_URI = "http://localhost:3000/user/oauth/kakao";
    String KAKAO_TOKEN_URI = "https://kauth.kakao.com/oauth/token";
    String KAKAO_CLIENT_SECRET = "Q3ZilYgFgGorxTFqcSds7r30RdTuaj5w";


    public SocialLoginDTO  getKakaoAccessToken(String code) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", KAKAO_CLINET_ID);
        params.add("redirect_uri", KAKAO_REDIRECT_URI);
        params.add("code", code);
        params.add("client_secret", KAKAO_CLIENT_SECRET);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, httpHeaders);

        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> accessTokenResponse = rt.exchange(KAKAO_TOKEN_URI, HttpMethod.POST, kakaoTokenRequest, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SocialLoginDTO socialLoginDTO;
        try {
            socialLoginDTO = objectMapper.readValue(accessTokenResponse.getBody(), SocialLoginDTO.class);
//            return socialLoginDTO.getAccess_token();
            return socialLoginDTO;
        } catch (JsonProcessingException e) {
            log.error("Error Kakao Access Token");
            return null;
        }
//        return accessTokenResponse.getBody();
    }

//    public ResponseEntity<?> kakaoLogin(String kakao) {
//        Account account = getKakaoInfo(kakaoAccessToken);
//
//        LoginResponseDto loginResponseDto = new LoginResponseDto();
//        loginResponseDto.setLoginSuccess(true);
//        loginResponseDto.setAccount(account);
//
//        Account existOwner = accountRepository.findById(account.getId()).orElse(null);
//        try {
//            if (existOwner == null) {
//                System.out.println("처음 로그인 하는 회원입니다.");
//                accountRepository.save(account);
//            }
//            loginResponseDto.setLoginSuccess(true);
//
//            return ResponseEntity.ok().headers(headers).body(loginResponseDto);
//
//        } catch (Exception e) {
//            loginResponseDto.setLoginSuccess(false);
//            return ResponseEntity.badRequest().body(loginResponseDto);
//        }
//    }
}
