package com.showback.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserAuthDTO {
    private Long authId;

    private String authName;

    private String authEmail;

    private String authPhone;

    private boolean smsChoice;

    private String validatePeriod;

    private Date authDate;

    private Long userId;

}

