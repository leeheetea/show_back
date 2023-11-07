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
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordRepository passwordRepository;
    private final UserAuthRepository userAuthRepository;

    // join
    public void register(User userEntity, Password passwordEntity, UserAuth userAuthEntity) {
        userRepository.save(userEntity);
        passwordRepository.save(passwordEntity);
        userAuthRepository.save(userAuthEntity);

        // if need return
        // 1. custom DTO
        // 2. Entity List
    }

    // login
    public User getByCredentials(final String username, final String password, final PasswordEncoder passwordEncoder){
        final User loginUser = userRepository.findByUsername(username);
        final Password loginPassword = passwordRepository.findByUser_UserId(loginUser.getUserId());

        if(loginUser != null && passwordEncoder.matches(password, loginPassword.getUserPassword())) {
            return loginUser;
        }
        return null;
    }

    // find id
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

    // pwd update
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

    // login jwt userId, pwd update
    public void updatePasswordByUserId(Long userId, String newPassword, PasswordEncoder passwordEncoder) {
        Password passwordEntity = passwordRepository.findByUser_UserId(userId);

        if(passwordEntity != null){
            passwordEntity.setUserPassword(passwordEncoder.encode(newPassword));
            passwordRepository.save(passwordEntity);
        }
    }

    // login jwt userId, email update
    public void updateEmailByUserId(Long userId, String newEmail) {
        UserAuth userAuthEntity = userAuthRepository.findByUser_UserId(userId);

        if(userAuthEntity != null) {
            userAuthEntity.setAuthEmail(newEmail);
            userAuthRepository.save(userAuthEntity);
        }
    }

    // login jw userId, check password
    public Password verifyPasswordBeforeUpdate(Long userId, String password, PasswordEncoder passwordEncoder) {
        Password passwordEntity = passwordRepository.findByUser_UserId(userId);

        if(passwordEntity != null && passwordEncoder.matches(password, passwordEntity.getUserPassword())){
            return passwordEntity;
        }

        return null;
    }

    public String getByEmail(Long userId) {
        UserAuth userAuth = userAuthRepository.findByUserId(userId);
        String userEmail = userAuth.getAuthEmail();
        return userEmail;
    }
}

