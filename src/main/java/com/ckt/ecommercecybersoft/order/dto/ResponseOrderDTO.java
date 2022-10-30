package com.ckt.ecommercecybersoft.order.dto;

import com.ckt.ecommercecybersoft.address.dto.AddressDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseOrderDTO {
    private AddressDTO address;
    private String comment;
    private String status;
    private long totalCost;
    private List<ResponseOrderItemDTO> orderItems;
}
