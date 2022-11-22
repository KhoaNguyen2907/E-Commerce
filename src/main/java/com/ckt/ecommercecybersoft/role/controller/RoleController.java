package com.ckt.ecommercecybersoft.role.controller;

import com.ckt.ecommercecybersoft.common.model.ResponseDTO;
import com.ckt.ecommercecybersoft.common.utils.ResponseUtils;
import com.ckt.ecommercecybersoft.role.dto.RoleDto;
import com.ckt.ecommercecybersoft.role.service.RoleService;
import com.ckt.ecommercecybersoft.role.utils.RoleUrlUtils;
import com.ckt.ecommercecybersoft.security.authorization.AdminOnly;
import com.ckt.ecommercecybersoft.user.model.response.OperationStatusModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(RoleUrlUtils.ROLE_API_V1)
public class RoleController {
    @Autowired
    private RoleService roleService;
    Logger logger = LoggerFactory.getLogger(RoleController.class);

    /**
     * Get all roles
     * @param pageSize page size default is 5.
     * @param pageNumber page number default is 1.
     * @return list of roles
     */
    @AdminOnly
    @GetMapping
    public ResponseEntity<ResponseDTO> getAllRoles(@RequestParam(defaultValue = "5") int pageSize, @RequestParam(defaultValue = "1") int pageNumber) {
        logger.info("Get all roles with page size: {} and page number: {} ", pageSize, pageNumber);
        List<RoleDto> roles = roleService.getAllRoles(Pageable.ofSize(pageSize).withPage(pageNumber-1));
        return ResponseUtils.get(roles, HttpStatus.OK);
    }

    /**
     * Create new role
     * @param roleDto role model with name, code and description
     * @return role information
     */
    @AdminOnly
    @PostMapping
    public ResponseEntity<ResponseDTO> createRole(@Valid @RequestBody RoleDto roleDto) {
        logger.info("Create new role with name: {}", roleDto.getName());
        RoleDto newRole = roleService.createRole(roleDto);
        logger.info("Create new role successfully with name: {} and code {}", newRole.getName(), newRole.getCode());
        return ResponseUtils.get(newRole, HttpStatus.CREATED);
    }

    /**
     *  Update role info
     *  @param roleDto role model with code is required
     * @return updated role information
     */
    @AdminOnly
    @PutMapping()
    public ResponseEntity<ResponseDTO> updateRole(@RequestBody RoleDto roleDto) {
        RoleDto updatedRole = roleService.updateRole(roleDto);
        return ResponseUtils.get(updatedRole, HttpStatus.OK);
    }

    /**
     * Delete role by code
     * @param code role code
     * @return operation status
     */
    @AdminOnly
    @DeleteMapping(path = RoleUrlUtils.BY_CODE)
    public ResponseEntity<ResponseDTO> deleteRole(@PathVariable String code) {
        boolean isDeleted = roleService.deleteRole(code);
        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setOperationName("DELETE");
        operationStatusModel.setOperationResult(isDeleted ? "SUCCESS" : "FAILED");
        return ResponseUtils.get(operationStatusModel, HttpStatus.OK);
    }

    @AdminOnly
    @GetMapping(path = RoleUrlUtils.SEARCH_ROLE)
    public ResponseEntity<ResponseDTO> searchRole(@RequestParam String keyword) {
        List<RoleDto> roles = roleService.searchRole(keyword);
        return ResponseUtils.get(roles, HttpStatus.OK);
    }


}
