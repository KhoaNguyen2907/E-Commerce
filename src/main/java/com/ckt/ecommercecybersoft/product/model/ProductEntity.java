package com.ckt.ecommercecybersoft.product.model;

import com.ckt.ecommercecybersoft.brand.model.BrandEntity;
import com.ckt.ecommercecybersoft.category.model.CategoryEntity;
import com.ckt.ecommercecybersoft.common.entity.BaseEntity;
import com.ckt.ecommercecybersoft.order.model.OrderItem;
import com.ckt.ecommercecybersoft.product.model.ProductCategoryEntity;
import com.ckt.ecommercecybersoft.product.util.ProductUtil;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.*;
import java.util.stream.Collectors;

@Setter
@Getter
@Entity
@Table(name = ProductUtil.Product.TABLE_NAME)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity extends BaseEntity {
    @Column(name = ProductUtil.Product.TITLE, nullable = false, unique = true)
//    @NotEmpty(message = "Title of product cannot be empty")
//    @UniqueElements(message = "Title of product duplicated")
    private String title;
    @Column(name = ProductUtil.Product.PRICE)
    private long price;
    @Column(name = ProductUtil.Product.DESCRIPTION)
    private String description;
    @Column(name = ProductUtil.Product.THUMBNAIL, length = 1024)
    private String thumbnailUrl;
    @Column(name = ProductUtil.Product.STOCK)
    private int stock;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ProductUtil.Product.BRAND_ID)
    private BrandEntity brand;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<ProductCategoryEntity> productCategoryEntities = new HashSet<>();
    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<OrderItem> orderItems = new HashSet<>();
    public ProductEntity addBrand(BrandEntity brandEntity){
        this.setBrand(brandEntity);
        brandEntity.getProductEntities().add(this);
        return this;
    }

    public ProductEntity addCategory(CategoryEntity categoryEntity) {
        ProductCategoryEntity productCategoryEntity =
                new ProductCategoryEntity(
                        this,
                        categoryEntity
                );
        this.productCategoryEntities.add(productCategoryEntity);
//        categoryEntity.getProductCategoryEntities().add(productCategoryEntity);
        return this;
    }

    public void removeCategory() {
        productCategoryEntities.forEach(productCategoryEntity -> {
            productCategoryEntity.setProduct(null);
            productCategoryEntity.setCategory(null);
        });
        productCategoryEntities.clear();

    }

    public List<UUID> getCategoryIds() {
        return productCategoryEntities.stream()
                .map(productCategoryEntity -> {
                    return productCategoryEntity.getCategory().getId();
                })
                .collect(Collectors.toList());
    }

    public List<CategoryEntity> getCategories() {
        return productCategoryEntities.stream()
                .map(productCategoryEntity -> {
                    return productCategoryEntity.getCategory();
                })
                .collect(Collectors.toList());
    }
}
