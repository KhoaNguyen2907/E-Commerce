package com.ckt.ecommercecybersoft.post.dto;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO implements Serializable {
    private UUID id;
    private String title;
    private String subtitle;
    private String code;
    private String content;
}