package com.showback.service;

import com.showback.dto.UserDTO;
import com.showback.model.LoginLog;
import com.showback.model.Password;
import com.showback.model.User;
import com.showback.model.UserAuth;
import com.showback.repository.LoginLogRepository;
import com.showback.repository.PasswordRepository;
import com.showback.repository.UserAuthRepository;
import com.showback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordRepository passwordRepository;
    private final UserAuthRepository userAuthRepository;
    private final LoginLogRepository loginLogRepository;

    // join
    public void register(User userEntity, Password passwordEntity, UserAuth userAuthEntity) {
        userRepository.save(userEntity);
        passwordRepository.save(passwordEntity);
        userAuthRepository.save(userAuthEntity);

        // if need return
        // 1. custom DTO
        // 2. Entity List
        // fact) need remake
    }

    // login
    public User getByCredentials(final String username, final String password, final PasswordEncoder passwordEncoder, String ipAddress, String userAgent){
        final User loginUser = userRepository.findByUsername(username);
        final Password loginPassword = passwordRepository.findByUser_UserId(loginUser.getUserId());
        LoginLog loginLog = new LoginLog();
        loginLog.setUser(loginUser);
        loginLog.setIpAddress(ipAddress);
        loginLog.setUserAgent(userAgent);
        loginLog.setLoginTime(new Date());

        LoginLog lastLoginLog = loginLogRepository.findTopByUserOrderByLoginTimeDesc(loginUser);

        if(loginUser != null && passwordEncoder.matches(password, loginPassword.getUserPassword())) {
            if(!lastLoginLog.getAccountStatus()) {
                loginLog.setAccountStatus(true);
                loginLog.setLoginFailureCount(lastLoginLog.getLoginFailureCount());
                loginLogRepository.save(loginLog);
                return  loginUser;
            } else {
                loginLog.setLoginFailureCount(0);
                loginLog.setAccountStatus(true);
                loginLogRepository.save(loginLog);
                return loginUser;
            }
        } else {
            if(lastLoginLog != null) {
                loginLog.setLoginFailureCount(lastLoginLog.getLoginFailureCount() + 1);
            } else {
                loginLog.setLoginFailureCount(1);
            }
            loginLog.setAccountStatus(false);
            loginLogRepository.save(loginLog);
            return null;
        }
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

    // username, name, email submit하면 email로 code send
    public Password requestPassword(final String username, final String name, final String email) {
        final Password resetPassword = passwordRepository.findByUser_UsernameAndUser_UserAuth_AuthNameAndUser_UserAuth_AuthEmail(username, name, email);

        if(resetPassword != null) {
            return resetPassword;
        }
        return null;
    }

    // pwd update
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

    public UserDTO verifyPasswordBeforeGetUser(Long userId, String password, PasswordEncoder passwordEncoder) {
        Password passwordEntity = passwordRepository.findByUser_UserId(userId);

        if(passwordEntity != null && passwordEncoder.matches(password, passwordEntity.getUserPassword())){
            User userEntity = userRepository.findById(userId).orElse(null);
            UserAuth userAuthEntity = userEntity.getUserAuth();
            return UserDTO.builder()
                    .username(userEntity.getUsername())
                    .name(userAuthEntity.getAuthName())
                    .email(userAuthEntity.getAuthEmail())
                    .phone(userAuthEntity.getAuthPhone())
                    .smscheck(userAuthEntity.isSmsChoice())
                    .isRadioChecked(userAuthEntity.getValidatePeriod())
                    .build();
        }

        return null;
    }
}


