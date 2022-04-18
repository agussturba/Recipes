package com.uade.recipes.model;

import com.uade.recipes.vo.UserVo;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String role;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String userName;
    @Column(nullable = false)
    private String password;
    private String name;
    @OneToOne
    private UserPhoto avatar;
    private boolean enabled;

    public UserVo toVO() {
        UserVo vo = new UserVo();
        vo.setId(id);
        vo.setName(name);
        vo.setRole(role);
        vo.setEmail(email);
        vo.setUserName(userName);
        vo.setPassword(password);
        if (this.avatar == null) {
            vo.setAvatarId(null);
        } else {
            vo.setAvatarId(avatar.getId());
        }
        return vo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

