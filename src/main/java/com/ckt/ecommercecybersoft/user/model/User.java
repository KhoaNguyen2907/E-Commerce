package com.ckt.ecommercecybersoft.user.model;

import com.ckt.ecommercecybersoft.common.entity.BaseEntity;
import com.ckt.ecommercecybersoft.role.model.Role;
import com.ckt.ecommercecybersoft.user.utils.UserExceptionUtils;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = UserEntity.TABLE_NAME)
public class User extends BaseEntity {
    @Column(name = UserEntity.USERNAME,unique = true, nullable = false)
    @Length(min = 5, max = 50, message = UserExceptionUtils.USERNAME_LENGTH)
    @NotBlank(message = UserExceptionUtils.USERNAME_NOT_BLANK)
    private String username;

    @Length(min = 5, max = 200, message = UserExceptionUtils.PASSWORD_LENGTH)
    @Column(name = UserEntity.PASSWORD, nullable = false)
    @NotBlank(message = UserExceptionUtils.PASSWORD_NOT_BLANK)
    private String password;

    @Column(name = UserEntity.EMAIL, unique = true, nullable = false)
    @Email(message = UserExceptionUtils.EMAIL_INVALID)
    @NotBlank (message = UserExceptionUtils.EMAIL_NOT_BLANK)
    private String email;

    @Column(name = UserEntity.NAME)
    @NotBlank (message = UserExceptionUtils.NAME_NOT_BLANK)
    private String name;

    @Column(name = UserEntity.AVATAR)
    private String avatar;

    @Column(name = UserEntity.STATUS)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name =UserEntity.EMAIL_VERIFIED)
    private boolean emailVerified = false;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = UserEntity.ROLE_ID)
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
        TEMPOARY_BLOCKED,
        PERMANENTLY_BLOCKED
    }


}
