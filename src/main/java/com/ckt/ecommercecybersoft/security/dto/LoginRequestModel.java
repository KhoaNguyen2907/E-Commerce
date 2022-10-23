package com.ckt.ecommercecybersoft.security.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginRequestModel {
    private String username;
    private String password;

}
