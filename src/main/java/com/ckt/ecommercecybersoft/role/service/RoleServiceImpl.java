package com.ckt.ecommercecybersoft.role.service;

import com.ckt.ecommercecybersoft.common.exception.NotFoundException;
import com.ckt.ecommercecybersoft.common.utils.ProjectMapper;
import com.ckt.ecommercecybersoft.role.dto.RoleDto;
import com.ckt.ecommercecybersoft.role.model.Role;
import com.ckt.ecommercecybersoft.role.repository.RoleRepository;
import com.ckt.ecommercecybersoft.role.utils.RoleExceptionUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Override
    @Cacheable(value = "roleList", key = "#root.methodName")
    public List<RoleDto> getAllRoles(Pageable pageable) {
        return findAllDto(pageable,RoleDto.class);
    }

    @Override
    @Cacheable(value = "role", key = "#roleCode")
    public RoleDto findByCode(String roleCode) {
        Role role = repository.findByCode(roleCode).orElseThrow();
        return mapper.map(role, RoleDto.class);
    }

    @Override
    @CacheEvict(value = "roleList", allEntries = true)
    public RoleDto createRole(RoleDto roleDto) {
        Role role = mapper.map(roleDto, Role.class);
        Role savedRole = repository.save(role);
        return mapper.map(savedRole, RoleDto.class);
    }

    @Override
    @CachePut(value = "role", key = "#roleDto.code")
    @CacheEvict(value = "roleList", allEntries = true)
    public RoleDto updateRole(RoleDto roleDto) {
        Role role = repository.findByCode(roleDto.getCode()).orElseThrow(() -> new NotFoundException(RoleExceptionUtils.ROLE_NOT_FOUND));
        roleDto.setId(role.getId());
        role = mapper.map(roleDto, Role.class);
        Role savedRole = repository.save(role);
        return mapper.map(savedRole, RoleDto.class);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "roleList", allEntries = true),
            @CacheEvict(value = "role", key = "#roleCode")
    })
    public boolean deleteRole(String roleCode) {
        Role role = repository.findByCode(roleCode).orElseThrow(() -> new NotFoundException(RoleExceptionUtils.ROLE_NOT_FOUND));
        repository.deleteById(role.getId());
        return true;
    }

    @Override
    public List<RoleDto> searchRole(String keyword) {
        List<Role> roles = repository.findByKeyword(keyword.toLowerCase());
        if (roles.isEmpty()) {
            return null;
        }
        return roles.stream().map(role -> mapper.map(role, RoleDto.class)).collect(Collectors.toList());
    }
}
