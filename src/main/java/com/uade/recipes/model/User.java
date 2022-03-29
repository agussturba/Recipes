package com.uade.recipes.model;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String role;
    private String email;
    @Column(name = "user_name")
    private String userName;
    private String password;
    private Boolean enabled;


    public User() {
    }

    public User(String userName, String password, Boolean enabled, String email) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.enabled = enabled;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }


    public boolean isEnabled() {
        return enabled;
    }

    public String getPassword() {
        return password;
    }


    public String getRol() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}

