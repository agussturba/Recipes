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

    public UserVo(User user) {
        idUser = user.getId();
        userName = user.getUserName();
        password = user.getPassword();
        email = user.getEmail();
        role = user.getRole();
    }

    public User toModel() {
        User user = new User();
        user.setId(idUser);
        user.setEmail(email);
        user.setPassword(password);
        user.setUserName(userName);
        user.setRole(role);
        return user;
    }
}
