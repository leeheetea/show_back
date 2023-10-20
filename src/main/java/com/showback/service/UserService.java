package com.showback.service;

import com.showback.DTO.UserDTO;
import com.showback.model.Password;
import com.showback.model.User;
import com.showback.model.UserAuth;
import com.showback.repository.PasswordRepository;
import com.showback.repository.UserAuthRepository;
import com.showback.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
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

    @Transactional
    public void register(UserDTO userDTO) {

        User userEntity = new User();
        userEntity.setUsername(userDTO.getUsername());
        userRepository.save(userEntity);

        Password passwordEntity = new Password();
        passwordEntity.setUser(userEntity);
        passwordEntity.setUserPassword(userDTO.getPassword());
        passwordRepository.save(passwordEntity);

        UserAuth userAuthEntity = new UserAuth();
        userAuthEntity.setUser(userEntity);
        userAuthEntity.setAuthName(userDTO.getName());
        userAuthEntity.setAuthEmail(userDTO.getEmail());
        userAuthEntity.setAuthPhone(userDTO.getPhone());
        userAuthEntity.setSmsChoice(userDTO.isSmscheck());
        userAuthEntity.setValidatePeriod(userDTO.getIsRadioChecked());
        userAuthRepository.save(userAuthEntity);
    }

    public void login(final String username, final String password, final PasswordEncoder passwordEncoder){
        final User loginUser = userRepository.findByUsername(username);
        final Password loginPassword = passwordRepository.findPasswordByUsername(username);

        if(loginUser != null && passwordEncoder.matches(password, loginPassword.getUserPassword())) {

        }
    }


}
