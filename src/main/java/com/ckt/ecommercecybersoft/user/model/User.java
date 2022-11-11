package com.ckt.ecommercecybersoft.user.model;

import com.ckt.ecommercecybersoft.common.entity.BaseEntity;
import com.ckt.ecommercecybersoft.role.model.Role;
import com.ckt.ecommercecybersoft.user.utils.UserExceptionUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Entity
@ToString
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

    @Column(name = UserColumn.STATUS)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = UserColumn.EMAIL_VERIFIED)
    private boolean emailVerified = false;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = UserColumn.ROLE_ID)
    private Role role;

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
