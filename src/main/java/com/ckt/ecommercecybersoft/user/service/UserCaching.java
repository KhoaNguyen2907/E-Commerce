package com.ckt.ecommercecybersoft.user.service;

import com.ckt.ecommercecybersoft.user.dto.UserDto;
import com.ckt.ecommercecybersoft.user.model.User;

public interface UserCaching {
    UserDto updateUserCache(User user);
    void deleteUserCache(User user);
}
