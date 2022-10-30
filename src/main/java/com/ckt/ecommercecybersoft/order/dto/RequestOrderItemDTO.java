package com.ckt.ecommercecybersoft.order.dto;

import com.ckt.ecommercecybersoft.product.model.ProductEntity;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestOrderItemDTO {
    private UUID productId;
    private int quantity;
}
