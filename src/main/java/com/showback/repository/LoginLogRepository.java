package com.showback.repository;

import com.showback.model.LoginLog;
import com.showback.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginLogRepository extends JpaRepository<LoginLog,Long> {

    // find user's last loginlog
    LoginLog findTopByUserOrderByLoginTimeDesc(User user);

    List<LoginLog> findByAccountStatus(Boolean accountStatus);
}
