package com.ckt.ecommercecybersoft.role.controller;

import com.ckt.ecommercecybersoft.common.exception.NotFoundException;
import com.ckt.ecommercecybersoft.common.model.ResponseDTO;
import com.ckt.ecommercecybersoft.common.utils.ProjectMapper;
import com.ckt.ecommercecybersoft.common.utils.ResponseUtils;
import com.ckt.ecommercecybersoft.role.dto.RoleDto;
import com.ckt.ecommercecybersoft.role.model.Role;
import com.ckt.ecommercecybersoft.role.service.RoleService;
import com.ckt.ecommercecybersoft.role.utils.RoleExceptionUtils;
import com.ckt.ecommercecybersoft.role.utils.RoleUrlUtils;
import com.ckt.ecommercecybersoft.security.authorization.AdminOnly;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(RoleUrlUtils.ROLE_API_V1)
public class RoleController {
    @Autowired
    private RoleService roleService;
    Logger logger = LoggerFactory.getLogger(RoleController.class);

    @AdminOnly
    @GetMapping

    public ResponseEntity<ResponseDTO> getAllRoles(@RequestParam(defaultValue = "5") int pageSize, @RequestParam(defaultValue = "1") int pageNumber) {
        logger.info("Get all roles with page size: {} and page number: {} ", pageSize, pageNumber);
        List<RoleDto> roles = roleService.findAllDto(Pageable.ofSize(pageSize).withPage(pageNumber-1), RoleDto.class);
        return ResponseUtils.get(roles, HttpStatus.OK);
    }

    @AdminOnly
    @PostMapping
    public ResponseEntity<ResponseDTO> createRole(@Valid @RequestBody RoleDto roleDto) {
        logger.info("Create new role with name: {}", roleDto.getName());
        RoleDto newRole = roleService.save(roleDto,Role.class);
        logger.info("Create new role successfully with name: {} and code {}", newRole.getName(), newRole.getCode());
        return ResponseUtils.get(newRole, HttpStatus.CREATED);
    }

    /**
     * Note: These method is not used in the project.
     */
//    @PutMapping(path = RoleUrlUtils.BY_ID)
//    public ResponseEntity<ResponseDTO> updateRole(@RequestBody RoleDto roleDto) {
//        RoleDto updatedRole = roleService.saveOrUpdate(roleDto, Role.class);
//        return ResponseUtils.get(updatedRole, HttpStatus.OK);
//    }
//
//    @DeleteMapping(path = RoleUrlUtils.BY_ID)
//    public ResponseEntity<ResponseDTO> deleteRole(@PathVariable UUID id) {
//        roleService.deleteById(id);
//        return ResponseUtils.get(null, HttpStatus.OK);
//    }


}
