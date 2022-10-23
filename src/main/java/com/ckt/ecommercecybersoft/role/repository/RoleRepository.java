package com.ckt.ecommercecybersoft.role.repository;

import com.ckt.ecommercecybersoft.role.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

}

