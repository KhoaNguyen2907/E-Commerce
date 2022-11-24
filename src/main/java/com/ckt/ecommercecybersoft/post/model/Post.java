package com.ckt.ecommercecybersoft.post.model;

import com.ckt.ecommercecybersoft.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = PostEntity.Post.TABLE_NAME)
public class Post extends BaseEntity {

    @Column(name = PostEntity.Post.TITLE)
    private String title;

    @Column(name = PostEntity.Post.SUBTITLE)
    private String subtitle;

    @Column(name = PostEntity.Post.CODE)
    private String code;

    @Column(name = PostEntity.Post.CONTENT)
    private String content;

    @OneToMany(mappedBy = PostEntity.CommentMappedPost.POST_MAPPED_COMMENT, fetch = FetchType.LAZY)
    private Set<Comment> comments = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = PostEntity.UserMappedPost.JOIN_TABLE_USER_ID)
    private User user;

    public void removeComment (Comment comment){
        this.comments.remove(comment);
    }

    public void addComment (Comment comment) {
        this.comments.add(comment);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if(obj == null || Hibernate.getClass(this) != Hibernate.getClass(obj)) {
            return false;
        }
        Post post = (Post) obj;
        return this.id != null && Objects.equals(this.id, post.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
