package com.uade.recipes.vo;


import com.uade.recipes.model.User;

public class UserVo {
    Integer idUser;
    String userName;
    String password;
    String email;
    String role;

    public UserVo() {
    }

    public UserVo(User user) {
        idUser=user.getId();
        userName=user.getUserName();
        password=user.getPassword();
        email=user.getEmail();
        role=user.getRol();
    }

    public Integer getIdUser() {
        return idUser;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User toModel() {
        User user = new User();
        if(idUser!=null){
            user.setId(idUser);
        }
         user.setEmail(email);
         user.setPassword(password);
         user.setUserName(userName);
         user.setRole(role);
         return user;
    }
}
