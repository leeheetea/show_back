package com.showback.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.showback.dto.SocialLoginDTO;
import com.showback.model.Password;
import com.showback.model.SocialLogin;
import com.showback.model.User;
import com.showback.repository.SocialLoginRepository;
import com.showback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final SocialLoginRepository socialLoginRepository;
    private final UserRepository userRepository;

    String KAKAO_CLINET_ID = "98fb1054fadbc801e5b9337e8492549d";
    String KAKAO_REDIRECT_URI = "http://localhost:3000/user/oauth/kakao";
    String KAKAO_TOKEN_URI = "https://kauth.kakao.com/oauth/token";
    String KAKAO_CLIENT_SECRET = "Q3ZilYgFgGorxTFqcSds7r30RdTuaj5w";

    // 사용자 정보가져오기
    String KAKAO_USER_DATA_URI = "https://kapi.kakao.com/v2/user/me";

    // 로그아웃
    String KAKAO_USER_LOGOUT_URI = "https://kapi.kakao.com/v1/user/logout";
    String KAKAO_OAUTH_LOGOUT_URI = "https://kauth.kakao.com/oauth/logout";
    String KAKAO_LOGOUT_REDIRECT_URI = "http://localhost:3000/user/oauth/kakao/logout";


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

    public String socialLoginInfo(SocialLoginDTO socialLoginDTO) {
        SocialLogin socialLogin = new SocialLogin();


        String idToken = socialLoginDTO.getId_token();
        String[] splitIdToken = idToken.split("\\.");
        String payload = new String(Base64.getDecoder().decode(splitIdToken[1]));
//        System.out.println("payload = " + payload);
        JSONObject payloadJson = new JSONObject(payload);

        SocialLogin existingSocialLogin = socialLoginRepository.findBySocialUserIdFromProvider(payloadJson.getString("email"));
        // if social join already done
        if (existingSocialLogin != null) {
            System.out.println("----already join");
            // token update ?
            existingSocialLogin.setAccessToken(socialLoginDTO.getAccess_token());
            socialLoginRepository.save(existingSocialLogin);
            User associatedUser = existingSocialLogin.getUser();

            return associatedUser.getUsername();
        }

        // new social join
//        User user = new User();
//        user.setUsername(payloadJson.getString("email"));
//        user.setLoginType(1);
//        userRepository.save(user);

//        socialLogin.setSocialCode(payloadJson.getString("iss"));
//        socialLogin.setSocialUserIdFromProvider(payloadJson.getString("email"));
//        socialLogin.setUser(user);
//        socialLoginRepository.save(socialLogin);
        else {
            System.out.println("----new join");
            User user = new User();
            user.setLoginType(1);
            userRepository.save(user);

            socialLogin.setAccessToken(socialLoginDTO.getAccess_token());
            socialLogin.setSocialCode(payloadJson.getString("iss"));
            socialLogin.setSocialUserIdFromProvider(payloadJson.getString("email"));
            socialLogin.setUser(user);
            socialLoginRepository.save(socialLogin);
            return payloadJson.getString("email");
        }
    }

    public User getByCredentials(final String username){
        final User loginUser = userRepository.findByUsername(username);

        if(loginUser != null && loginUser.getLoginType() == 1) {
            return loginUser;
        }

        return null;
    }

    public SocialLogin socialLogout(Long userId){
        final SocialLogin socialLogin = socialLoginRepository.findByUser_UserId(userId);

        if(socialLogin != null) {
            return socialLogin;
        }

        return null;
    }

    //https://kauth.kakao.com/oauth/logout?client_id=98fb1054fadbc801e5b9337e8492549d&logout_redirect_uri=http://localhost:3000/user/oauth/kakao/logout
    public URI initiateKakaoLogout() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(KAKAO_OAUTH_LOGOUT_URI)
                .queryParam("client_id", KAKAO_CLINET_ID)
                .queryParam("logout_redirect_uri", KAKAO_LOGOUT_REDIRECT_URI);
        return builder.build().toUri();
    }

}
