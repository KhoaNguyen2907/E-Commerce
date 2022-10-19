package com.ckt.ecommercecybersoft.common.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
public class ResponseDTO {
    private Object content;
    private boolean hasErrors;
    private List<String> errors;
    private String timestamp;
    private int status;

}
