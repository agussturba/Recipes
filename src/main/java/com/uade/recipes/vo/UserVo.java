package com.uade.recipes.vo;


import com.uade.recipes.model.User;
import lombok.Data;

@Data
public class UserVo {
    Integer idUser;
    String userName;
    String password;
    String email;
    String role;
    boolean enabled;
    String name;
    String avatar;

    public UserVo(User user) {
        idUser = user.getId();
        userName = user.getUserName();
        password = user.getPassword();
        email = user.getEmail();
        role = user.getRole();
        enabled = user.isEnabled();
        name = user.getName();
        avatar = user.getAvatar();
    }

    public User toModel() {
        User user = new User();
        user.setId(idUser);
        user.setEmail(email);
        user.setPassword(password);
        user.setUserName(userName);
        user.setRole(role);
        user.setEnabled(enabled);
        user.setName(name);
        user.setAvatar(avatar);
        return user;
    }
}
