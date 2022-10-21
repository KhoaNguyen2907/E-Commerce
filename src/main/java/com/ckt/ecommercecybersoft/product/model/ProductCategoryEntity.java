package com.ckt.ecommercecybersoft.product.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = ProductCategoryUtil.ProductCategory.TABLE_NAME)
@Data
public class ProductCategoryEntity {
    @EmbeddedId
    private ProductCategoryId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    private ProductEntity product;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("categoryId")
    private CategoryEntity category;

    public ProductCategoryEntity() {
        product.getId();
    }
}
