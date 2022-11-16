package com.ckt.ecommercecybersoft.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemRequestDTO {
    private UUID productId;
    private int quantity;
}
