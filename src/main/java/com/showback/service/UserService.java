package com.showback.service;

import com.showback.model.Password;
import com.showback.model.User;
import com.showback.model.UserAuth;
import com.showback.repository.PasswordRepository;
import com.showback.repository.UserAuthRepository;
import com.showback.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordRepository passwordRepository;
    @Autowired
    private UserAuthRepository userAuthRepository;

    public User registerUser(String username,
                         String password,
                         String name,
                         String email,
                         String smsChoice,
                         String validatePeriod) {

        User user = new User();
        user.setUserName(username);

        Password userPassword = new Password();
        userPassword.setUserPassword(password);

        UserAuth userAuth = new UserAuth();
        userAuth.setAuthName(name);
        userAuth.setAuthName(email);
        userAuth.setAuthName(smsChoice);
        userAuth.setAuthName(validatePeriod);

        user.setPassword(userPassword);
        userPassword.setUser(user);

        userRepository.save(user);
        passwordRepository.save(userPassword);
        userAuthRepository.save(userAuth);

        return user;
    }

}
