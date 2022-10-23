package com.ckt.ecommercecybersoft.product.model;

import com.ckt.ecommercecybersoft.common.entity.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = BrandUtil.Brand.TABLE_NAME)
public class BrandEntity extends BaseEntity {
    @Column(name = BrandUtil.Brand.TITLE)
    private String title;
    @Column(name = BrandUtil.Brand.THUMBNAIL, length = 1024)
    private String thumbnailUrl;
}
