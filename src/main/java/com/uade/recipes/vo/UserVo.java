package com.uade.recipes.vo;


import com.uade.recipes.model.User;
import com.uade.recipes.model.UserPhoto;
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
    Integer avatarId;



    public User toModel() {
        User user = new User();
        user.setId(idUser);
        user.setEmail(email);
        user.setPassword(password);
        user.setUserName(userName);
        user.setRole(role);
        user.setEnabled(enabled);
        user.setName(name);
        user.setAvatar(null);
        return user;
    }
}
