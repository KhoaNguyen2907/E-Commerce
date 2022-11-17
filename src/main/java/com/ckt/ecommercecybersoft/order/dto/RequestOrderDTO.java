package com.ckt.ecommercecybersoft.order.dto;

import com.ckt.ecommercecybersoft.address.dto.AddressDTO;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestOrderDTO {
    private AddressDTO address;
    private String comment;
    private String phone;
    private long totalCost;
    private List<RequestOrderItemDTO> orderItems;

//    public Status getStatus() {
//        System.out.println("status: " + status.toUpperCase());
//        try {
//            return OrderEntity.Status.valueOf(status);
//        } catch (IllegalArgumentException e) {
//            return Status.FAILED;
//        } catch (NullPointerException e) {
//            return Status.FAILED;
//        } finally {
//            return Status.FAILED;
//        }
////        return OrderEntity.Status.valueOf(status);
//    }
//
//    public void setStatus(Status status) {
//        this.status = status.getStatus();
//    }
}
