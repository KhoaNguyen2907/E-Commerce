package com.ckt.ecommercecybersoft.post.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.UUID;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO implements Serializable {

    private UUID id;

    @Length(min = 5, max = 100, message = "{post.title.size}")
    private String title;

    @Length(min = 5, max = 500, message = "{post.subtitle.size}")
    private String subtitle;

    private String code;

    @NotBlank
    private String imageUrl;

    @NotBlank(message = "{post.content.blank}")
    private String content;

    @NotBlank(message = "{post.userId.blank}")
    private UUID userId;
}