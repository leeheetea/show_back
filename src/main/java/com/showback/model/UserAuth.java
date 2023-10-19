package com.showback.model;

import com.showback.model.Order;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users_auth")
@Data
public class UserAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authId;

    private String authName;

    private String authEmail;

    private String authPhone;

    private String smsChoice;

    private String validatePeriod;

    private Date authDate;

    @OneToMany(mappedBy = "userAuth", cascade = CascadeType.ALL)
    private List<Order> orderList;

    @OneToMany(mappedBy = "userAuth", cascade = CascadeType.ALL)
    private List<Inquiry> inquiryList;

    @OneToMany(mappedBy = "userAuth", cascade = CascadeType.ALL)
    private List<Notice> noticeList;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
