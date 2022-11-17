package com.ckt.ecommercecybersoft.order.dto;

import com.ckt.ecommercecybersoft.product.model.ProductSimpleInfoModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseOrderItemDTO {
    private ProductSimpleInfoModel product;
    private int quantity;
}
