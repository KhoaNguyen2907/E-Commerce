package com.ckt.ecommercecybersoft.user.service;

import com.ckt.ecommercecybersoft.common.service.GenericService;
import com.ckt.ecommercecybersoft.role.dto.RoleDto;
import com.ckt.ecommercecybersoft.user.controller.UserController;
import com.ckt.ecommercecybersoft.user.dto.UserDto;
import com.ckt.ecommercecybersoft.user.dto.UserDtoWithCart;
import com.ckt.ecommercecybersoft.user.dto.UserDtoWithOrders;
import com.ckt.ecommercecybersoft.user.dto.UserDtoWithPosts;
import com.ckt.ecommercecybersoft.user.model.User;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * User service
 * @author KhoaNguyen
 */
public interface UserService extends GenericService<User, UserDto, UUID> {

    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto);

    boolean deleteUser(UUID id);


    UserDto findByUsername(String username);

    UserDto findUserById(UUID id);

    boolean verifyEmailToken(String token);

    boolean requestPasswordReset(String email);

    UserDto resetPassword(String token, String password);

    boolean verifyPasswordResetToken(String token);

    UserDto changeRole(UUID id, RoleDto roleDto);

    boolean changePassword(UUID id, String oldPassword, String newPassword);

    Optional<UserDto> getCurrentUser();

    List<UserDto> searchUser(String keyword, Pageable pageable);

    UserDtoWithCart getUserWithCart(UUID id);

    UserDtoWithPosts getCurrentUserWithPosts(UUID id);

    UserDtoWithOrders getUserWithOrders(UUID id);
}

