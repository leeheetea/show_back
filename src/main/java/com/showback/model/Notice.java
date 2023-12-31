package com.showback.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "notices")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;

    private String noticeTitle;

    private String noticeDetail;

    private String noticeImgUrl;

    private Date noticeDate;

    @ManyToOne
    @JoinColumn(name = "auth_id")
    private UserAuth userAuth;


}
