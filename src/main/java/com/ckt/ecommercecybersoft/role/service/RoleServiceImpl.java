package com.ckt.ecommercecybersoft.role.service;

import com.ckt.ecommercecybersoft.common.utils.ProjectMapper;
import com.ckt.ecommercecybersoft.role.model.Role;
import com.ckt.ecommercecybersoft.role.repository.RoleRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;
    private final ProjectMapper mapper;

    public RoleServiceImpl(RoleRepository repository, ProjectMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public JpaRepository<Role, UUID> getRepository() {
        return repository;
    }

    @Override
    public ProjectMapper getMapper() {
        return mapper;
    }
}
