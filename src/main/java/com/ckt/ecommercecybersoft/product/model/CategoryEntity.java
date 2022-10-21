package com.ckt.ecommercecybersoft.product.model;

import com.ckt.ecommercecybersoft.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = CategoryUtil.Category.TABLE_NAME)
public class CategoryEntity extends BaseEntity {
    @Column(name = CategoryUtil.Category.TITLE)
    private String title;
//    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
//    private Set<ProductCategoryEntity> productCategoryEntities;
}
