package com.showback.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "login_logs")
public class LoginLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loginLogId;

    private String ipAddress;

    private Date loginTime;

    private Date logoutTime;

    private Date loginFailureCount;

    private Boolean accountStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
