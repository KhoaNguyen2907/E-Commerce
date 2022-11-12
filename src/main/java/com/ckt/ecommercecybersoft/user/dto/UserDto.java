package com.ckt.ecommercecybersoft.user.dto;

import com.ckt.ecommercecybersoft.address.dto.AddressDTO;
import com.ckt.ecommercecybersoft.role.dto.RoleDto;
import com.ckt.ecommercecybersoft.user.model.User;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;

    private String username;

    private String password;

    private String email;

    private String name;

    private String avatar;

    private AddressDTO address;

    private RoleDto role;

    private User.Status status;

    private String emailVerificationToken;

    private boolean emailVerified;

    private String createdBy;

    private String lastModifiedBy;

    private LocalDateTime createdAt;

    private LocalDateTime lastModifiedAt;


}
