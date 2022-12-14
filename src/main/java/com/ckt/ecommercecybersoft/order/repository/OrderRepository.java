package com.ckt.ecommercecybersoft.order.repository;

import com.ckt.ecommercecybersoft.order.model.OrderEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
    @Query("SELECT o FROM OrderEntity o WHERE o.user.id = ?1")
    List<OrderEntity> findAllByUserId(UUID id, Pageable pageable);
}
