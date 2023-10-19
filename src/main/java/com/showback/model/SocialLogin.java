package com.showback.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "social_login")
public class SocialLogin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long socialLoginId;

    private String socialCode;

    private String socialUserIdFromProvider;

    private String accessToken;

    private Date socialUpdateDate;

    @OneToOne
    private User user;
}
