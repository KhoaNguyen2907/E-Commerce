package com.ckt.ecommercecybersoft.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductSimpleInfoModel {
    private UUID id;
    private String title;
    private long price;
    private String thumbnailUrl;
}
