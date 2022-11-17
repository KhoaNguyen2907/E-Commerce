package com.ckt.ecommercecybersoft.order.model;

import com.ckt.ecommercecybersoft.address.dto.AddressDTO;
import com.ckt.ecommercecybersoft.order.dto.ResponseOrderItemDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderSimpleInfoModel {
    private AddressDTO address;
    private String comment;
    private String status;
    private String phone;
    private long totalCost;
    private List<ResponseOrderItemDTO> orderItems;
}
