package com.showback.service;

import com.showback.dto.UserDTO;
import com.showback.model.Password;
import com.showback.model.User;
import com.showback.model.UserAuth;
import com.showback.repository.PasswordRepository;
import com.showback.repository.UserAuthRepository;
import com.showback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordRepository passwordRepository;
    private final UserAuthRepository userAuthRepository;

    public void register(User userEntity, Password passwordEntity, UserAuth userAuthEntity) {
        userRepository.save(userEntity);
        passwordRepository.save(passwordEntity);
        userAuthRepository.save(userAuthEntity);

        // if need return
        // 1. custom DTO
        // 2. Entity List
    }

    public User getByCredentials(final String username, final String password, final PasswordEncoder passwordEncoder){
        final User loginUser = userRepository.findByUsername(username);
        final Password loginPassword = passwordRepository.findByUser_UserId(loginUser.getUserId());

        if(loginUser != null && passwordEncoder.matches(password, loginPassword.getUserPassword())) {
            return loginUser;
        }

        return null;
    }

    public UserDTO retrieveUsername(final String name, final String email) {
        final User findUsername = userRepository.findByUserAuth_AuthNameAndUserAuth_AuthEmail(name, email);
        final UserAuth userAuth = userAuthRepository.findByUser_UserId(findUsername.getUserId());

        if(findUsername != null) {
            return UserDTO.builder()
                    .username(findUsername.getUsername())
                    .authDate(userAuth.getAuthDate())
                    .build();
        }

        return null;
    }

    // 아이디 이름 이메일 입력하면 이메일로 인증코드 날리고 체크하기
//    public Password retrievePassword(final String username, final String name, final String email) {
//        final Password resetPassword = passwordRepository.findByUser_UsernameAndUserAuth_AuthNameAndUserAuth_AuthEmail(username, name, email);
//
//        if(resetPassword != null) {
//            return resetPassword;
//        }
//
//        return null;
//
//    }

    // 비밀번호 수정
    @Transactional
    public Password updatePasswordByUsername(final String username, final String newPassword, PasswordEncoder passwordEncoder) {
        final Password passwordEntity = passwordRepository.findByUser_Username(username);

//        if(passwordEntity != null && passwordEncoder.matches(newPassword, passwordEntity.getUserPassword())) {
        if(passwordEntity != null){
            passwordEntity.setUserPassword(passwordEncoder.encode(newPassword));
            passwordRepository.save(passwordEntity);
            return passwordEntity;
        }

        return null;
    }

    // jwt에서 userId 추출해서 비밀번호 수정
    public void updatePasswordByUserId(Long userId, String newPassword, PasswordEncoder passwordEncoder) {
        Password passwordEntity = passwordRepository.findByUser_UserId(userId);

        if(passwordEntity != null){
            passwordEntity.setUserPassword(passwordEncoder.encode(newPassword));
            passwordRepository.save(passwordEntity);
        }
    }




}

