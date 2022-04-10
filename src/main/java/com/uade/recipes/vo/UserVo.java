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
    String avatar;



    public User toModel(UserPhoto userPhoto) {
        User user = new User();
        user.setId(idUser);
        user.setEmail(email);
        user.setPassword(password);
        user.setUserName(userName);
        user.setRole(role);
        user.setEnabled(enabled);
        user.setName(name);
        user.setAvatar(userPhoto);
        return user;
    }
}
