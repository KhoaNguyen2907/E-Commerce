package com.ckt.ecommercecybersoft.order.model;

import com.ckt.ecommercecybersoft.common.entity.BaseEntity;
import com.ckt.ecommercecybersoft.order.constant.OrderConstant;
import com.ckt.ecommercecybersoft.product.model.ProductCategoryEntity;
import com.ckt.ecommercecybersoft.product.model.ProductEntity;
import com.ckt.ecommercecybersoft.product.util.ProductCategoryUtil;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = OrderConstant.OrderItem.TABLE_NAME)
@Data
@NoArgsConstructor
public class OrderItem {
    @EmbeddedId
    private OrderItemId orderItemId;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productEntityId")
    private ProductEntity productEntity;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId(value = "orderEntityId")
    private OrderEntity orderEntity;
    @Column(name = OrderConstant.OrderItem.QUANTITY)
    private int quantity;
    @Column(name = OrderConstant.OrderItem.COST)
    private long cost;

    public OrderItem(ProductEntity product,
                     OrderEntity order,
                     int quantity) {
        this.orderItemId = new OrderItemId(product.getId(), order.getId());
        this.productEntity = product;
        this.orderEntity = order;
        this.quantity = quantity;
        this.cost = productEntity.getPrice() * quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productEntity, orderEntity);
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
        OrderItem other = (OrderItem) obj;
        return Objects.equals(productEntity, other.productEntity)
                && Objects.equals(orderEntity, other.orderEntity);
    }
}
