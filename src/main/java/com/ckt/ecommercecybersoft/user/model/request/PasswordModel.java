package com.ckt.ecommercecybersoft.user.model.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PasswordModel {
    @Length(min = 5, max = 50, message = "Username must have length between {min} and {max} characters")
    @NotBlank
    private String password;

}
