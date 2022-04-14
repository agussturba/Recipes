package com.uade.recipes.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class UserPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @OneToOne
    private User user;
    private String photoUrl;
    private String extension;


}
