package com.ckt.ecommercecybersoft.category.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private UUID id;
    @NotBlank (message = "Name is required")
    private String title;
}
