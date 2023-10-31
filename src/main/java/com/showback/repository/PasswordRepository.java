package com.showback.repository;

import com.showback.model.Password;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordRepository extends JpaRepository<Password, Long> {
    Password findByUser_UserId(Long userId);
//    Password findByUser_UsernameAndUserAuth_AuthNameAndUserAuth_AuthEmail(String username, String name, String email);
    Password findByUser_Username(String username);

    Password findByUserPassword(String password);

    Password findByUser_UsernameAndUser_UserAuth_AuthNameAndUser_UserAuth_AuthEmail(String username, String authName, String authEmail);
}
