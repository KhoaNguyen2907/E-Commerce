package com.ckt.ecommercecybersoft.product.model;

import com.ckt.ecommercecybersoft.category.model.CategoryEntity;
import com.ckt.ecommercecybersoft.product.util.ProductCategoryUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = ProductCategoryUtil.ProductCategory.TABLE_NAME)
@Data
@NoArgsConstructor
//@AllArgsConstructor
public class ProductCategoryEntity {
    @EmbeddedId
    private ProductCategoryId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    private ProductEntity product;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("categoryId")
    private CategoryEntity category;

    public ProductCategoryEntity(ProductEntity product, CategoryEntity category) {
        this.id = new ProductCategoryId(product.getId(), category.getId());
        this.product = product;
        this.category = category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, category);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProductCategoryEntity other = (ProductCategoryEntity) obj;
        return Objects.equals(product, other.product)
                && Objects.equals(category, other.category);
    }
}
