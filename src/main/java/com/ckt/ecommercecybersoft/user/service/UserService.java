package com.ckt.ecommercecybersoft.user.service;

import com.ckt.ecommercecybersoft.common.service.GenericService;
import com.ckt.ecommercecybersoft.user.dto.UserDto;
import com.ckt.ecommercecybersoft.user.model.User;

import java.util.UUID;

public interface UserService extends GenericService<User, UserDto, UUID> {
    UserDto createUser(UserDto userDto);

    UserDto findByUsername(String username);

    boolean verifyEmailToken(String token);

    boolean requestPasswordReset(String email);

    UserDto resetPassword(String token, String password);

    boolean verifyPasswordResetToken(String token);
}

