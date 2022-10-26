package com.ckt.ecommercecybersoft.security.dto;

import com.ckt.ecommercecybersoft.user.utils.UserExceptionUtils;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginRequestModel {
    @NotBlank(message = UserExceptionUtils.USERNAME_NOT_BLANK)
    @Length(min = 5, max = 50, message = UserExceptionUtils.USERNAME_LENGTH)
    private String username;
    @NotBlank(message = UserExceptionUtils.PASSWORD_NOT_BLANK)
    @Length(min = 5, max = 50, message = UserExceptionUtils.PASSWORD_LENGTH)
    private String password;

}
