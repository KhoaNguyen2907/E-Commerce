package com.ckt.ecommercecybersoft.user.model.request;

import com.ckt.ecommercecybersoft.address.dto.AddressDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoModel {
    private UUID id;
    private String email;
    private String name;
    private String avatar;
    private AddressDTO address;

}
