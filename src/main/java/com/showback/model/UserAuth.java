package com.showback.model;

import com.showback.model.Order;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users_auth")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class UserAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authId;

    private String authName;

    private String authEmail;

    private String authPhone;

    private boolean smsChoice;

    private String validatePeriod;

    @CreatedDate
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

    @OneToMany(mappedBy = "userAuth", cascade = CascadeType.ALL)
    private List<Review> reviews;
}
