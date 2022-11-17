package com.ckt.ecommercecybersoft.cart.dto;

import com.ckt.ecommercecybersoft.product.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponseDTO implements Serializable {
    private ProductDTO product;
    private int quantity;
    private long totalPrice;
}
