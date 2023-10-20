package com.showback.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "passwords")
@Getter
@Setter
public class Password {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long passwordId;

    private String userPassword;

    private String salt;

    private Date updateDate;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}