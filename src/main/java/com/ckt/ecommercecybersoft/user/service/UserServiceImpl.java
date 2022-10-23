package com.ckt.ecommercecybersoft.user.service;

import com.ckt.ecommercecybersoft.common.exception.NotFoundException;
import com.ckt.ecommercecybersoft.common.utils.ProjectMapper;
import com.ckt.ecommercecybersoft.user.dto.UserDto;
import com.ckt.ecommercecybersoft.user.model.User;
import com.ckt.ecommercecybersoft.user.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final ProjectMapper mapper;

    public UserServiceImpl(UserRepository repository, ProjectMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public JpaRepository<User, UUID> getRepository() {
        return repository;
    }

    @Override
    public ProjectMapper getMapper() {
        return mapper;
    }


    @Override
    public UserDto findByUsername(String username) {
        return mapper.map(
                repository.findByUsername(username)
                        .orElseThrow(() -> new NotFoundException("User not found")), UserDto.class);
    }
}
