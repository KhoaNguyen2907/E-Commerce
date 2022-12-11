package com.ckt.ecommercecybersoft.order.dto;

import com.ckt.ecommercecybersoft.address.dto.AddressDTO;
import com.ckt.ecommercecybersoft.user.model.response.UserSimpleInfoModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseOrderDTO {
    private UUID id;
    private AddressDTO address;
    private String comment;
    private String status;
    private String phone;
    private long totalCost;
    private List<ResponseOrderItemDTO> orderItems;
    private UserSimpleInfoModel user;
}
