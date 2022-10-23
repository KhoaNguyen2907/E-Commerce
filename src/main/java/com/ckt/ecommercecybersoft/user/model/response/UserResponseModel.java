package com.ckt.ecommercecybersoft.user.model.response;

import com.ckt.ecommercecybersoft.role.dto.RoleDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseModel {
    private UUID id;

    private String username;

    private String email;

    private String name;

    private String avatar;

    private RoleDto role;
}
