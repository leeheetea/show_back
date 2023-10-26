package com.showback.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "passwords")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)

public class Password {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long passwordId;

    private String userPassword;

    private String salt;

    @LastModifiedDate
    private Date updateDate;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}