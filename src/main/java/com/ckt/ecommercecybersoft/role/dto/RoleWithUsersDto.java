package com.ckt.ecommercecybersoft.role.dto;

import com.ckt.ecommercecybersoft.role.utils.RoleExceptionUtils;
import com.ckt.ecommercecybersoft.user.dto.UserDto;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.Set;

public class RoleWithUsersDto {
    @Length(min = 2, max = 18, message = RoleExceptionUtils.NAME_LENGTH)
    @NotBlank(message = RoleExceptionUtils.NAME_NOT_BLANK)
    private String name;

    @Length(min = 2, max = 8, message = RoleExceptionUtils.CODE_LENGTH)
    @NotBlank(message = RoleExceptionUtils.CODE_NOT_BLANK)
    private String code;

    private String description;

    private Set<UserDto> users;
}
