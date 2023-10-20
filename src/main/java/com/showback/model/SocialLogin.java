package com.showback.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
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
