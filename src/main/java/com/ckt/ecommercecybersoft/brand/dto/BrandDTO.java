package com.ckt.ecommercecybersoft.brand.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandDTO {
    private UUID id;
    private String title;
    private String thumbnail;
}
