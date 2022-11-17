package com.ckt.ecommercecybersoft.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

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
    @JsonProperty("isContentArray")
    private boolean contentArray;
    private int status;
}
