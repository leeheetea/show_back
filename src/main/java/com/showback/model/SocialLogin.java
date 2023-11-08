package com.showback.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

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

    @LastModifiedDate
    private Date socialUpdateDate;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
