package com.ckt.ecommercecybersoft.category.repository;

import com.ckt.ecommercecybersoft.category.model.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID> {
}
