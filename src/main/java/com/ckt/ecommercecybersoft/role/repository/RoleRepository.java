package com.ckt.ecommercecybersoft.role.repository;

import com.ckt.ecommercecybersoft.role.dto.RoleDto;
import com.ckt.ecommercecybersoft.role.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findByCode(String user);

    @Query("SELECT r FROM Role r WHERE lower(r.code)  LIKE %?1% OR r.name LIKE %?1%")
    List<Role> findByKeyword(String keyword);
}

