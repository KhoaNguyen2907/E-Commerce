package com.ckt.ecommercecybersoft.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO {
    private Object content;
    private boolean hasErrors;
    private List<Error> errors;
    private long timestamp;
    private boolean isContentArray;
    private int status;

}
