package com.ckt.ecommercecybersoft.user.dto;

import com.ckt.ecommercecybersoft.role.dto.RoleDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;

    private String username;

    private String password;

    private String email;

    private String name;

    private String avatar;

    private RoleDto role;

    private String status;

    private String emailVerificationToken;

    private boolean emailVerificationSatus = false;

}
