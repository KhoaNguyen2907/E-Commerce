package com.ckt.ecommercecybersoft.user.service;

import com.ckt.ecommercecybersoft.common.utils.ProjectMapper;
import com.ckt.ecommercecybersoft.user.dto.UserDto;
import com.ckt.ecommercecybersoft.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
public class UserCachingImpl implements UserCaching {
    @Autowired
    ProjectMapper mapper;

    @Override
    @Caching(
            put = {
                    @CachePut(value = "users", key = "#user.id"),
                    @CachePut(value = "users", key = "#user.username")
            }
    )
    public UserDto updateUserCache(User user) {
        return mapper.map(user, UserDto.class);
    }

    @Override
    @Caching (
            evict = {
                    @CacheEvict (value = "users", key = "#user.username"),
                    @CacheEvict (value = "users", key = "#user.id"),
            }
    )
    public void deleteUserCache(User user) {
    }
}
