package com.ckt.ecommercecybersoft.role.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    @Length(min = 2, max = 18, message = "Role name must have length between {min} and {max} characters")
    private String name;

    @Length(min = 2, max = 8, message = "Role code must have length between {min} and {max} characters")
    private String code;

    private String description;
}

