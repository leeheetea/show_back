package com.showback.model;

import com.showback.model.Order;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users_auth")
@Getter
@Setter
public class UserAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authId;

    private String authName;

    private String authEmail;

    private String authPhone;

    private boolean smsChoice;

    private String validatePeriod;

    private Date authDate;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "userAuth", cascade = CascadeType.ALL)
    private List<Order> orderList;

    @OneToMany(mappedBy = "userAuth", cascade = CascadeType.ALL)
    private List<Inquiry> inquiryList;

    @OneToMany(mappedBy = "userAuth", cascade = CascadeType.ALL)
    private List<Notice> noticeList;


}
