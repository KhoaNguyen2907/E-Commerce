package com.ckt.ecommercecybersoft.role.service;

import com.ckt.ecommercecybersoft.common.service.GenericService;
import com.ckt.ecommercecybersoft.role.dto.RoleDto;
import com.ckt.ecommercecybersoft.role.model.Role;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface RoleService extends GenericService<Role, RoleDto, UUID> {
    List<RoleDto> getAllRoles(Pageable pageable);
    RoleDto findByCode(String code);
    RoleDto createRole(RoleDto roleDto);
    RoleDto updateRole(RoleDto roleDto);
    boolean deleteRole(String roleCode);
    List<RoleDto> searchRole(String keyword);
}
