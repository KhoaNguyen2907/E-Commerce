package com.ckt.ecommercecybersoft.user.dto;

import com.ckt.ecommercecybersoft.address.dto.AddressDTO;
import com.ckt.ecommercecybersoft.post.model.Post;
import com.ckt.ecommercecybersoft.role.dto.RoleDto;
import com.ckt.ecommercecybersoft.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoWithPosts {
    private UUID id;

    private String username;

    private String password;

    private String email;

    private String name;

    private String avatar;

    private AddressDTO address;

    private RoleDto role;

    private List<Post> cart;

    private User.Status status;

    private String emailVerificationToken;

    private boolean emailVerified;

    private String createdBy;

    private String lastModifiedBy;

    private LocalDateTime createdAt;

    private LocalDateTime lastModifiedAt;
}