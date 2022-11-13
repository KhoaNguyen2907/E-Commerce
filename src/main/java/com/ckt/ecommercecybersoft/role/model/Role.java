package com.ckt.ecommercecybersoft.role.model;

import com.ckt.ecommercecybersoft.common.entity.BaseEntity;
import com.ckt.ecommercecybersoft.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;


@Entity
@Table(name = "role")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Cacheable
public class Role extends BaseEntity {
    @Column(name = RoleColumn.NAME, nullable = false)
    private String name;

    @Column(name = RoleColumn.CODE, unique = true, nullable = false)
    private String code;

    @Column(name = RoleColumn.DESCRIPTION)
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    private Set<User> users = new LinkedHashSet<>();

    public void addUser(User user) {
        users.add(user);
        user.setRole(this);
    }
}
