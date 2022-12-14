package com.ckt.ecommercecybersoft.address.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO implements Serializable {
    private UUID id;
    private String street;
    private String district;
    private String city;
    private String province;
    private String zipCode;
}
