package com.uade.recipes.model;

import com.uade.recipes.vo.UserVo;
import lombok.Data;

import javax.persistence.*;


@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String role;
    private String email;
    private String userName;
    private String password;
    private String name;
    @OneToOne
    private UserPhoto avatar;
    private boolean enabled;

    public UserVo toVO(){
        UserVo vo = new UserVo();
        vo.setName(name);
        vo.setRole(role);
        vo.setEmail(email);
        vo.setUserName(userName);
        vo.setPassword(password);
        vo.setAvatarId(avatar.getId());

        return vo;
    }

}

