package com.ckt.ecommercecybersoft.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Error {
    private int errorCode;
    private String errorMessage;
}
