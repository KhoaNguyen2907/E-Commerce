package com.ckt.ecommercecybersoft.product.model;

import com.ckt.ecommercecybersoft.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = ProductUtil.Product.TABLE_NAME)
public class ProductEntity extends BaseEntity {
    @Column(name = ProductUtil.Product.TITLE, nullable = false, unique = true)
    private String title;
    @Column(name = ProductUtil.Product.PRICE)
    private double price;
    @Column(name = ProductUtil.Product.DESCRIPTION)
    private String description;
    @Column(name = ProductUtil.Product.THUMBNAIL, length = 1024)
    private String thumbnailUrl;
    @Column(name = ProductUtil.Product.STOCK)
    private int stock;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ProductUtil.Product.BRAND_ID)
    private BrandEntity brand;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<ProductCategoryEntity> productCategoryEntities;
}
