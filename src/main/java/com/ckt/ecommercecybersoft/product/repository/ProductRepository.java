package com.ckt.ecommercecybersoft.product.repository;

import com.ckt.ecommercecybersoft.product.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
}
