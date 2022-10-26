package com.ckt.ecommercecybersoft.user.model.request;

import com.ckt.ecommercecybersoft.role.dto.RoleDto;
import com.ckt.ecommercecybersoft.user.utils.UserExceptionUtils;
import com.ckt.ecommercecybersoft.user.validation.annotation.UniqueEmail;
import com.ckt.ecommercecybersoft.user.validation.annotation.UniqueUsername;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestModel {
    private UUID id;

    @Length(min = 5, max = 50, message = UserExceptionUtils.USERNAME_LENGTH)
    @UniqueUsername
    @NotBlank
    private String username;

    @Length(min = 5, max = 50, message = UserExceptionUtils.PASSWORD_LENGTH)
    @NotBlank
    private String password;

    @NotBlank
    @UniqueEmail
    @Email(message = UserExceptionUtils.EMAIL_INVALID)
    private String email;

    @NotBlank(message = UserExceptionUtils.NAME_NOT_BLANK)
    private String name;

    private String avatar;

    private RoleDto role;


}
