package com.showback.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "inquiries")
public class Inquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inquiryId;

    private String inquiryTitle;

    private String inquiryDetail;

    private String inquiryAnswer;

    private Date inquiryDate;

    @ManyToOne
    @JoinColumn(name = "auth_id")
    private UserAuth userAuth;
}
