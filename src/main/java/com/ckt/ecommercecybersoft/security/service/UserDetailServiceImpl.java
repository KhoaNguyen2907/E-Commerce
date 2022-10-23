package com.ckt.ecommercecybersoft.security.service;

import com.ckt.ecommercecybersoft.user.dto.UserDto;
import com.ckt.ecommercecybersoft.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto user = userService.findByUsername(username);
        return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }
}

