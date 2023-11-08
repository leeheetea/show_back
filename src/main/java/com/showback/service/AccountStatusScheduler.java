package com.showback.service;

import com.showback.model.LoginLog;
import com.showback.repository.LoginLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountStatusScheduler {

    private final LoginLogRepository loginLogRepository;

    @Scheduled(fixedRate = 3600000) // 3600000 ms = 1 hour
    public void updateAccountStatus(){
        List<LoginLog> expiredLoginLog = loginLogRepository.findByAccountStatus(true);

        Date currentDate = new Date();
        for(LoginLog loginLog: expiredLoginLog) {
            if(isMoreThan24Hours(loginLog.getLoginTime(), currentDate)){
                loginLog.setAccountStatus(false);
                loginLogRepository.save(loginLog);
            }

        }
    }

    private boolean isMoreThan24Hours(Date loginTime, Date currentTime) {
        long diff = currentTime.getTime() - loginTime.getTime();
        return diff > 86399999; // 86400000 ms = 24 hours
    }
}
