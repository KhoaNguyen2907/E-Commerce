package com.ckt.ecommercecybersoft.order.model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemId implements Serializable {
    @Column(name = "product_id")
    @Type(type = "uuid-char")
    private UUID productEntityId;
    @Column(name = "order_id")
    @Type(type = "uuid-char")
    private UUID orderEntityId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        OrderItemId that = (OrderItemId) o;
        return Objects.equals(productEntityId, that.productEntityId) &&
                Objects.equals(orderEntityId, that.orderEntityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productEntityId, orderEntityId);
    }
}
