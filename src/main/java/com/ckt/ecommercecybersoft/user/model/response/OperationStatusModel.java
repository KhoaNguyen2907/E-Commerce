package com.ckt.ecommercecybersoft.user.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OperationStatusModel {
    private String operationResult;
    private String operationName;

}
