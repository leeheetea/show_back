package com.showback.repository;

import com.showback.model.LoginLog;
import com.showback.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginLogRepository extends JpaRepository<LoginLog,Long> {

    // find user's last loginlog
    LoginLog findTopByUserOrderByLoginTimeDesc(User user);
}
