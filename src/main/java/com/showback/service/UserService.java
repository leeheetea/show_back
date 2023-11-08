package com.showback.service;

import com.showback.dto.UserDTO;
import com.showback.model.*;
import com.showback.repository.*;
import com.showback.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordRepository passwordRepository;
    private final UserAuthRepository userAuthRepository;
    private final LoginLogRepository loginLogRepository;
    private final TermsOfServiceRepository termsOfServiceRepository;
    private final UserAgreementRepository userAgreementRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final TokenProvider tokenProvider;

    // join
    public UserDTO register(UserDTO userDTO) {
//        userRepository.save(userEntity);
//        passwordRepository.save(passwordEntity);
//        userAuthRepository.save(userAuthEntity);

        User userSocialEntity = userRepository.findByLoginTypeAndUsernameIsNull(1);
        if(userSocialEntity != null) {
            userSocialEntity.setUsername(userDTO.getUsername());

            Password passwordEntity = new Password();
            passwordEntity.setUser(userSocialEntity);
            passwordEntity.setUserPassword(passwordEncoder.encode(userDTO.getPassword()));

            UserAuth userAuthEntity = new UserAuth();
            userAuthEntity.setUser(userSocialEntity);
            userAuthEntity.setAuthName(userDTO.getName());
            userAuthEntity.setAuthEmail(userDTO.getEmail());
            userAuthEntity.setAuthPhone(userDTO.getPhone());
            userAuthEntity.setSmsChoice(userDTO.isSmscheck());
            userAuthEntity.setValidatePeriod(userDTO.getIsRadioChecked());

            userRepository.save(userSocialEntity);
            passwordRepository.save(passwordEntity);
            userAuthRepository.save(userAuthEntity);

            for(Map.Entry<String, Boolean> entry : userDTO.getTermsChecked().entrySet()) {
                String termCode = entry.getKey();
                Boolean agreed = entry.getValue();

                TermsOfService termsOfService = termsOfServiceRepository.findByTermCode(termCode);
                if (termsOfService != null) {
                    UserAgreement userAgreement = new UserAgreement();
                    userAgreement.setUser(userSocialEntity);
                    userAgreement.setTermsOfService(termsOfService);
                    userAgreement.setAgreed(agreed);
                    userAgreement.setAgreementDate(new Date());

                    userAgreementRepository.save(userAgreement);
                }
                else{
                    throw new RuntimeException("No terms found for termCode: " + termCode);
                }
            }

            UserDTO responseUserDTO = new UserDTO();
            responseUserDTO.setName(userDTO.getName());
            return responseUserDTO;
        }

        User userEntity = new User();
        userEntity.setUsername(userDTO.getUsername());

        Password passwordEntity = new Password();
        passwordEntity.setUser(userEntity);
        passwordEntity.setUserPassword(passwordEncoder.encode(userDTO.getPassword()));

        UserAuth userAuthEntity = new UserAuth();
        userAuthEntity.setUser(userEntity);
        userAuthEntity.setAuthName(userDTO.getName());
        userAuthEntity.setAuthEmail(userDTO.getEmail());
        userAuthEntity.setAuthPhone(userDTO.getPhone());
        userAuthEntity.setSmsChoice(userDTO.isSmscheck());
        userAuthEntity.setValidatePeriod(userDTO.getIsRadioChecked());

        userRepository.save(userEntity);
        passwordRepository.save(passwordEntity);
        userAuthRepository.save(userAuthEntity);

        for(Map.Entry<String, Boolean> entry : userDTO.getTermsChecked().entrySet()) {
            String termCode = entry.getKey();
            Boolean agreed = entry.getValue();

            TermsOfService termsOfService = termsOfServiceRepository.findByTermCode(termCode);
            if (termsOfService != null) {
                UserAgreement userAgreement = new UserAgreement();
                userAgreement.setUser(userEntity);
                userAgreement.setTermsOfService(termsOfService);
                userAgreement.setAgreed(agreed);
                userAgreement.setAgreementDate(new Date());

                userAgreementRepository.save(userAgreement);
            }
            else{
                throw new RuntimeException("No terms found for termCode: " + termCode);
            }
        }

        UserDTO responseUserDTO = new UserDTO();
        responseUserDTO.setName(userDTO.getName());
        return responseUserDTO;
    }

    // login
    public User getByCredentials(final String username, final String password, final PasswordEncoder passwordEncoder, String ipAddress, String userAgent){
        final User loginUser = userRepository.findByUsername(username);
        final Password loginPassword = passwordRepository.findByUser_UserId(loginUser.getUserId());

        LoginLog lastLoginLog = loginLogRepository.findTopByUserOrderByLoginTimeDesc(loginUser);
        int lastFailureCount = (lastLoginLog != null) ? lastLoginLog.getLoginFailureCount() : 0;

        LoginLog loginLog = new LoginLog();
        loginLog.setUser(loginUser);
        loginLog.setIpAddress(ipAddress);
        loginLog.setUserAgent(userAgent);
        loginLog.setLoginTime(new Date());

        if(passwordEncoder.matches(password, loginPassword.getUserPassword())) {
            loginLog.setAccountStatus(true);
            if(lastLoginLog != null && lastLoginLog.getLogoutTime() != null) {
                loginLog.setLoginFailureCount(0);
            } else if(lastLoginLog != null && !lastLoginLog.getAccountStatus()) {
                loginLog.setLoginFailureCount(lastFailureCount);
            } else {
                loginLog.setLoginFailureCount(0);
            }
            loginLogRepository.save(loginLog);
            return loginUser;
        } else {
            loginLog.setAccountStatus(false);
            loginLog.setLoginFailureCount(lastFailureCount + 1);
            loginLogRepository.save(loginLog);
            return null;
        }
    }

    // find id
    public UserDTO retrieveUsername(final String name, final String email) {
        final User findUsername = userRepository.findByUserAuth_AuthNameAndUserAuth_AuthEmail(name, email);
        if (findUsername == null) {
            return null;
        }

        final UserAuth userAuth = userAuthRepository.findByUser_UserId(findUsername.getUserId());

        if(userAuth != null) {
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

    public String logout(String token) {
        String userId = tokenProvider.validateAndGetUserId(token);
        if(userId != null) {
            User user = userRepository.findById(Long.parseLong(userId))
                    .orElseThrow(()-> new EntityNotFoundException("User not found with id: " + userId));
            if (user != null) {
                LoginLog lastLoginLog = loginLogRepository.findTopByUserOrderByLoginTimeDesc(user);
                if(lastLoginLog != null) {
                    lastLoginLog.setLogoutTime(new Date());
                    lastLoginLog.setAccountStatus(false);
                    loginLogRepository.save(lastLoginLog);
                    return user.getUsername();
                }
            }
        }
        // logout fail
        return null;
    }

    // token validation check 동시로그인 방지 추가
    public boolean isTokenValid(String token){
        String userId = tokenProvider.validateAndGetUserId(token);
        if(userId != null) {
            User user = userRepository.findById(Long.parseLong(userId))
                    .orElseThrow(()-> new EntityNotFoundException("User not found with id: " + userId));
            LoginLog lastLoginLog = loginLogRepository.findTopByUserOrderByLoginTimeDesc(user);
            if(lastLoginLog != null && lastLoginLog.getAccountStatus()) {
                return true;
            }
        }
        return false;
    }

    // login failure count
    public int getLastLoginFailureCount(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) return 0; // Or handle this case differently if necessary

        LoginLog lastLoginlog = loginLogRepository.findTopByUserOrderByLoginTimeDesc(user);
        if (lastLoginlog != null) {
            return lastLoginlog.getLoginFailureCount();
        }
        return 0;
    }

    public User getByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public LoginLog getLastLoginLog(User user){
        return loginLogRepository.findTopByUserOrderByLoginTimeDesc(user);
    }

    public String findName(Long userId){
        UserAuth userAuth = userAuthRepository.findByUser_UserId(userId);

        return userAuth.getAuthName();
    }
}


