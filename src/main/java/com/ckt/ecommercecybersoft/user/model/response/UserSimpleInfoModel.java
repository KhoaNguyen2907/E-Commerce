package com.ckt.ecommercecybersoft.user.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSimpleInfoModel {
    private UUID id;
    private String name;
    private String avatar;
}
