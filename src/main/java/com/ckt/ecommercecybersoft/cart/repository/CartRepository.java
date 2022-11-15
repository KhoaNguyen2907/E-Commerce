package com.ckt.ecommercecybersoft.cart.repository;

import com.ckt.ecommercecybersoft.cart.model.CartItemEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<CartItemEntity, UUID> {
//    @Query("FROM CartItemEntity cart WHERE cart.productEntity.id = :product_id")
    @Query(value = "SELECT c FROM CartItemEntity c " +
            "WHERE c.user.id= ?1 " +
            "AND c.productEntity.id = ?2")
    List<CartItemEntity> findByUserIdAndProductId(UUID userId, UUID productId);
    List<CartItemEntity> findByUserId(UUID userId);
    List<CartItemEntity> findByQuantity(int quantity);
}
