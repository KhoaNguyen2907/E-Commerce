package com.ckt.ecommercecybersoft.user.model.request;

import com.ckt.ecommercecybersoft.role.dto.RoleDto;
import com.ckt.ecommercecybersoft.user.validation.annotation.UniqueEmail;
import com.ckt.ecommercecybersoft.user.validation.annotation.UniqueUsername;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestModel {
    private UUID id;

    @Length(min = 5, max = 50, message = "Username must have length between {min} and {max} characters")
    @UniqueUsername
    private String username;

    @Length(min = 5, max = 50, message = "Username must have length between {min} and {max} characters")
    private String password;

    @UniqueEmail
    @Email(message = "Email is not valid")
    private String email;

    private String name;

    private String avatar;

    private RoleDto role;

}
