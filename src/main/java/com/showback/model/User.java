package com.showback.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String username;

    private int loginType;

    @OneToMany(mappedBy = "user")
    private List<LoginLog> loginLogs = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    private Password password;

    @OneToOne(mappedBy = "user")
    private SocialLogin socialLogin;

    @OneToMany(mappedBy = "user")
    private List<UserAgreement> userAgreements = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    private UserAuth userAuth;

}