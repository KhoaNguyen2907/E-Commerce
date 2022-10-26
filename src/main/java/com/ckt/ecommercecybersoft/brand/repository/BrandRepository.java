package com.ckt.ecommercecybersoft.brand.repository;

import com.ckt.ecommercecybersoft.brand.model.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BrandRepository extends JpaRepository<BrandEntity, UUID> {

}
