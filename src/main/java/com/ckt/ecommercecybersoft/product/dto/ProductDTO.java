package com.ckt.ecommercecybersoft.product.dto;

import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
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
    transient private List<UUID> categoryIds;
}

