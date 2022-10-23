package com.ckt.ecommercecybersoft.role.model;

import com.ckt.ecommercecybersoft.common.entity.BaseEntity;
import com.ckt.ecommercecybersoft.user.model.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;


@Entity
@Table(name = "role")
@Getter
@Setter
public class Role extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "role")
    private Set<User> users = new LinkedHashSet<>();

    public void addUser(User user) {
        users.add(user);
        user.setRole(this);
    }
}
