package com.ckt.ecommercecybersoft.post.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO implements Serializable {

    private UUID id;

    @NotBlank(message = "{comment.cmt.blank}")
    private String cmt;

    private UUID userId;

    private UUID postId;
}