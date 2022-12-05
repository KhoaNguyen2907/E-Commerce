package com.ckt.ecommercecybersoft.user.model.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Setter
public class PasswordModel {
    private UUID id;

    @Length(min = 5, max = 50, message = "Password must have length between {min} and {max} characters")
    @NotBlank
    private String password;

    @Length(min = 5, max = 50, message = "Password must have length between {min} and {max} characters")
    @NotBlank
    private String oldPassword;





}
