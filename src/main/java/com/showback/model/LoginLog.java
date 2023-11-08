package com.showback.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "login_logs")
public class LoginLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loginLogId;

    private String ipAddress;

    private String userAgent;

    private Date loginTime;

    @Column(nullable = true)
    private Date logoutTime;

    private int loginFailureCount;

    private Boolean accountStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void incrementLoginFailureCount() {
        this.loginFailureCount += 1;
    }
}
