package com.showback.repository;

import com.showback.model.SocialLogin;
import com.showback.model.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialLoginRepository extends JpaRepository<SocialLogin, Long> {
    SocialLogin findBySocialUserIdFromProvider(String socialUserIdFromProvider);
    SocialLogin findByUser_UserId(Long userId);

}
