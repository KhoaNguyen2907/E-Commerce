package com.ckt.ecommercecybersoft.cart.dto;

import com.ckt.ecommercecybersoft.product.dto.ProductDTO;
import com.ckt.ecommercecybersoft.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO implements Serializable {
    private UUID id;
    private ProductDTO product;
    private int quantity;
    private UserDto user;
    private long totalPrice;
}
