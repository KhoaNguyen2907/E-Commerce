package com.ckt.ecommercecybersoft.post.model;

import com.ckt.ecommercecybersoft.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


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

    @Override
    public boolean equals(Object obj) {
        Post postObj = (Post) obj;
        return super.equals(obj) && postObj.getCode().equals(this.code);
    }
}
