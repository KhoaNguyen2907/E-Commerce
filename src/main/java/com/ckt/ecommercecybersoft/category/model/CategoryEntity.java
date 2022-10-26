package com.ckt.ecommercecybersoft.category.model;

import com.ckt.ecommercecybersoft.common.entity.BaseEntity;
import com.ckt.ecommercecybersoft.product.model.ProductCategoryEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = CategoryUtil.Category.TABLE_NAME)
public class CategoryEntity extends BaseEntity {
    @Column(name = CategoryUtil.Category.TITLE)
    private String title;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<ProductCategoryEntity> productCategoryEntities;
}
