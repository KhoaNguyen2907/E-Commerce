package com.ckt.ecommercecybersoft.user.service;

import com.ckt.ecommercecybersoft.common.service.GenericService;
import com.ckt.ecommercecybersoft.role.dto.RoleDto;
import com.ckt.ecommercecybersoft.user.dto.UserDto;
import com.ckt.ecommercecybersoft.user.model.User;

import java.util.UUID;

/**
 * User service
 * @author KhoaNguyen
 */
public interface UserService extends GenericService<User, UserDto, UUID> {
    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto);

    UserDto findByUsername(String username);

    boolean verifyEmailToken(String token);

    boolean requestPasswordReset(String email);

    UserDto resetPassword(String token, String password);

    boolean verifyPasswordResetToken(String token);

    UserDto changeRole(UUID id, RoleDto roleDto);

    void changePassword(UUID id, String oldPassword, String newPassword);

    UserDto getCurrentUser();
}

