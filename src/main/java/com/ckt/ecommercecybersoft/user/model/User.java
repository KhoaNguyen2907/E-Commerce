package com.ckt.ecommercecybersoft.user.model;

import com.ckt.ecommercecybersoft.common.entity.BaseEntity;
import com.ckt.ecommercecybersoft.role.model.Role;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Column(name = "username",unique = true, nullable = false)
    @Length(min = 5, max = 50, message = "Username must have length between {min} and {max} characters")
    private String username;

    @Length(min = 5, max = 200, message = "Password must have length between {min} and {max} characters")
    @Column(name = "password")
    private String password;

    @Column(name = "email", unique = true, nullable = false)
    @Email(message = "Email is not valid")
    @NotNull
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    private String emailValidationToken;
//
//    @Column(name ="email_validated" )
//    private boolean emailValidationStatus;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
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
