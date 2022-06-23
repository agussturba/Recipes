package com.uade.recipes.vo;


import com.uade.recipes.model.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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
    String avatar;
    LocalDateTime registrationTimestamp;



    public User toModel() {
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setUserName(userName);
        user.setRole(role);
        user.setEnabled(enabled);
        user.setName(name);
        if (avatar == null) {
            user.setAvatar("https://res.cloudinary.com/fransiciliano/image/upload/v1656009088/default_avatar.jpg");
        } else {
            user.setAvatar(avatar);
        }
        user.setPassword(password);
        user.setRegistrationTimestamp(registrationTimestamp);
        return user;
    }
}
