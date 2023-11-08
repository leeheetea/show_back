package com.showback.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users_agreements")
public class UserAgreement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userAgreementId;

    private boolean agreed;

    @CreatedDate
    private Date agreementDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "terms_Id")
    private TermsOfService termsOfService;
}
