package com.ckt.ecommercecybersoft.address.model;

import com.ckt.ecommercecybersoft.address.constant.AddressConstant;
import com.ckt.ecommercecybersoft.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Setter
@Getter
@Entity
@Table(name = AddressConstant.TABLE_NAME)

public class AddressEntity extends BaseEntity {
    @Column(name = AddressConstant.STREET)
    private String street;
    @Column(name = AddressConstant.DISTRICT)
    private String district;
    @Column(name = AddressConstant.CITY)
    private String city;
    @Column(name = AddressConstant.PROVINCE)
    private String province;
    @Column(name = AddressConstant.ZIP_CODE)
    private String zipCode;
}
