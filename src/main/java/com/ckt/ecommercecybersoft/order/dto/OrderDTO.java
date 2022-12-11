package com.ckt.ecommercecybersoft.order.dto;

import com.ckt.ecommercecybersoft.address.dto.AddressDTO;
import com.ckt.ecommercecybersoft.order.model.OrderEntity;
import com.ckt.ecommercecybersoft.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private UUID id;
    private AddressDTO address;
    private String comment;
    private String phone;
    private OrderEntity.Status status;
    private long totalCost;
    private List<OrderItemDto> orderItems;
    private UserDto user;
    private LocalDateTime createdAt;
}
