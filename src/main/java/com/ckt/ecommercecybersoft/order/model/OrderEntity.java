package com.ckt.ecommercecybersoft.order.model;

import com.ckt.ecommercecybersoft.address.model.AddressEntity;
import com.ckt.ecommercecybersoft.common.entity.BaseEntity;
import com.ckt.ecommercecybersoft.order.constant.OrderConstant;
import com.ckt.ecommercecybersoft.user.model.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = OrderConstant.Order.TABLE_NAME)
public class OrderEntity extends BaseEntity {
    //    @Column(name = OrderConstant.Order.NAME)
//    private String name;
    @Column(name = OrderConstant.Order.COMMENT)
    private String comment;
    @Column(name = OrderConstant.Order.STATUS)
    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;
    @Column(name = OrderConstant.Order.TOTAL_COST)
    private long totalCost;
    @OneToMany(mappedBy = "orderEntity",
            cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new LinkedList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private AddressEntity address;

    @Column(name = OrderConstant.Order.PHONE)
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

//    transient private List<RequestOrderItemDTO> requestOrderItemDTOs;
    public String getStatus() {
        return status.getStatus();
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrderEntity(this);
//        this.productCategoryEntities.add(productCategoryEntity);
//        categoryEntity.getProductCategoryEntities().add(productCategoryEntity);
    }

//    public void removeCategory() {
//        orderItems.forEach(productCategoryEntity -> {
//            productCategoryEntity.setProduct(null);
//            productCategoryEntity.setCategory(null);
//        });
//        productCategoryEntities.clear();
//
//    }

//    public List<ResponseOrderItemDTO> getResponseOrderItemDTOs() {
//        return orderItems.stream()
//                .map(orderItem -> {
//                    return new ResponseOrderItemDTO(
//                            orderItem.getProductEntity(),
//                            orderItem.getQuantity()
//                    );
//                })
//                .collect(Collectors.toList());
//    }

    public void setTotalCost() {

    }

    public enum Status {
        PENDING,
        DELIVERING,
        SUCCESS,
        FAILED;

        public String getStatus() {
            return this.name();
        }
    }
}
