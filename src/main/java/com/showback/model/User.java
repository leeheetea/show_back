package com.showback.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String username;

    private int loginType;

    @OneToMany(mappedBy = "user")
    private List<LoginLog> loginLogs = new ArrayList<>();

    @JsonManagedReference
    @OneToOne(mappedBy = "user")
    private Password password;

    @OneToOne(mappedBy = "user")
    private SocialLogin socialLogin;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<UserAgreement> userAgreements = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    @JsonManagedReference
    private UserAuth userAuth;



}