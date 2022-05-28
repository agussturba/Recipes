package com.uade.recipes.model;

import com.uade.recipes.vo.UserVo;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@SecondaryTable(name = "user_addition", pkJoinColumns = @PrimaryKeyJoinColumn(name = "user_id"))

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String role;
    @Column(unique = true)
    private String email;
    @Column(nullable = false)
    private String userName;
    private String name;
    @OneToOne
    private UserPhoto avatar;
    private boolean enabled;

    @Column(table = "user_addition")
    private String password;

    @Column(table = "user_addition")
    private LocalDateTime registrationTimestamp;

    public UserVo toVO() {
        UserVo vo = new UserVo();
        vo.setId(id);
        vo.setName(name);
        vo.setRole(role);
        vo.setEmail(email);
        vo.setUserName(userName);
        vo.setRegistrationTimestamp(registrationTimestamp);
        vo.setEnabled(enabled);
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

