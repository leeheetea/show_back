package com.showback.service;

import com.showback.model.Password;
import com.showback.model.User;
import com.showback.model.UserAuth;
import com.showback.repository.PasswordRepository;
import com.showback.repository.UserAuthRepository;
import com.showback.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordRepository passwordRepository;
    @Autowired
    private UserAuthRepository userAuthRepository;


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
}
