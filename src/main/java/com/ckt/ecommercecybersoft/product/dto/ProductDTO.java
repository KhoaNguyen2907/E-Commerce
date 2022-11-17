package com.ckt.ecommercecybersoft.product.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO implements Serializable {
    private UUID id;
    @NotEmpty(message = "{product.title.not.empty}")
    private String title;
    @Min(value = 0, message = "{product.price.min}")
    private long price;
    private String description;
    private String thumbnailUrl;
    @Min(value = 0, message = "{product.stock.min}")
    private int stock;
    private UUID brandId;
    @NotEmpty(message = "{product.category.not.empty}")
    transient private List<UUID> categoryIds;
}

