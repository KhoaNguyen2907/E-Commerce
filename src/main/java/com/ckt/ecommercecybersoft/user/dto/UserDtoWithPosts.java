package com.ckt.ecommercecybersoft.user.dto;

import com.ckt.ecommercecybersoft.post.dto.PostDTO;
import com.ckt.ecommercecybersoft.role.dto.RoleDto;
import com.ckt.ecommercecybersoft.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoWithPosts {
    private UUID id;

    private String username;

    private String email;

    private String name;

    private String avatar;

    private RoleDto role;

    private List<PostDTO> posts;

    private User.Status status;

}
