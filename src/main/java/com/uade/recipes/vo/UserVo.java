package com.uade.recipes.vo;


import com.uade.recipes.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserVo {
    Integer id;
    String userName;
    String password;
    String email;
    String role;
    boolean enabled;
    String name;
    Integer avatarId;



    public User toModel() {
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setUserName(userName);
        user.setRole(role);
        user.setEnabled(true);
        user.setName(name);
        user.setAvatar(null);
        return user;
    }
}
