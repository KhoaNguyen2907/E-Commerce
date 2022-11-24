package com.ckt.ecommercecybersoft.post.model;

import com.ckt.ecommercecybersoft.common.entity.BaseEntity;
import com.ckt.ecommercecybersoft.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = PostEntity.Comment.TABLE_NAME)
public class Comment extends BaseEntity {

    @Column(name = PostEntity.Comment.COMMENT, nullable = false)
    @NotBlank(message = "{comment.cmt.blank}")
    private String cmt;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = PostEntity.CommentMappedPost.JOIN_TABLE_POST_ID)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = PostEntity.UserMappedComment.JOIN_TABLE_USER_ID)
    private User user;

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
