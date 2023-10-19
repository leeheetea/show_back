package com.showback.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "passwords")
@Data
public class Password {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long passwordId;

    private String password;

    private String salt;

    private Date updateDate;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}