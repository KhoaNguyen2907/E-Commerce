package com.ckt.ecommercecybersoft.order.dto;

import com.ckt.ecommercecybersoft.product.model.ProductEntity;
import com.ckt.ecommercecybersoft.product.util.ProductUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {
    private ProductEntity product;
    private int quantity;
}
