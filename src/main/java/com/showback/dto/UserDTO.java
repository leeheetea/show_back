package com.showback.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private String token;
    private String username;
    private String password;
    private String password_verified;
    private String name;
    private String email;
    private String phone;
    private boolean smscheck;
    private String isRadioChecked;
    private Date authDate;

}
