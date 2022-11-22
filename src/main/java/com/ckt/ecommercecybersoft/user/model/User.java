package com.ckt.ecommercecybersoft.user.model;

import com.ckt.ecommercecybersoft.address.model.AddressEntity;
import com.ckt.ecommercecybersoft.cart.model.CartItemEntity;
import com.ckt.ecommercecybersoft.common.entity.BaseEntity;
import com.ckt.ecommercecybersoft.order.model.OrderEntity;
import com.ckt.ecommercecybersoft.post.model.Comment;
import com.ckt.ecommercecybersoft.post.model.Post;
import com.ckt.ecommercecybersoft.post.model.PostEntity;
import com.ckt.ecommercecybersoft.role.model.Role;
import com.ckt.ecommercecybersoft.user.utils.UserExceptionUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@Entity
@ToString(exclude = "role")
@SQLDelete(sql = "UPDATE users SET status = 'PERMANENTLY_BLOCKED' WHERE id = ?")
@Where(clause = "status <> 'PERMANENTLY_BLOCKED'")
@Table(name = UserColumn.TABLE_NAME)
public class User extends BaseEntity {
    @Column(name = UserColumn.USERNAME,unique = true, nullable = false)
    @Length(min = 5, max = 50, message = UserExceptionUtils.USERNAME_LENGTH)
    @NotBlank(message = UserExceptionUtils.USERNAME_NOT_BLANK)
    private String username;

    @Length(min = 5, max = 200, message = UserExceptionUtils.PASSWORD_LENGTH)
    @Column(name = UserColumn.PASSWORD, nullable = false)
    @NotBlank(message = UserExceptionUtils.PASSWORD_NOT_BLANK)
    private String password;

    @Column(name = UserColumn.EMAIL, unique = true, nullable = false)
    @Email(message = UserExceptionUtils.EMAIL_INVALID)
    @NotBlank (message = UserExceptionUtils.EMAIL_NOT_BLANK)
    private String email;

    @Column(name = UserColumn.NAME)
    @NotBlank (message = UserExceptionUtils.NAME_NOT_BLANK)
    private String name;

    @Column(name = UserColumn.AVATAR)
    private String avatar;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = UserColumn.ADDRESS)
    private AddressEntity address;

    @Column(name = UserColumn.STATUS)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = UserColumn.EMAIL_VERIFIED)
    private boolean emailVerified = false;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = UserColumn.ROLE_ID)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<OrderEntity> orders;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartItemEntity> cart;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = PostEntity.UserMappedPost.USER_MAPPED_POST, cascade = CascadeType.ALL)
    private List<Post> posts;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = PostEntity.UserMappedComment.USER_MAPPED_COMMENT)
    private List<Comment> comments;

    @PreRemove
    private void preRemove() {
        this.setStatus(Status.PERMANENTLY_BLOCKED);
    }

    @Override
    public boolean equals(Object obj) {
        User userObj = (User) obj;
        return super.equals(obj)
                && userObj.getUsername().equals(this.username)
                && userObj.getEmail().equals(this.email);
    }

    public enum  Status {
        ACTIVE,
        TEMPORARY_BLOCKED,
        PERMANENTLY_BLOCKED
    }


}
