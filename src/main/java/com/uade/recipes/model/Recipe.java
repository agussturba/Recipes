package com.uade.recipes.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String name;
    private String description;
    @OneToMany
    private List<RecipePhoto> recipePhotos;
    @OneToOne
    private Dish dish;
    @OneToOne
    private User user;
    private Integer peopleAmount;
    private Double portions;
    @OneToMany
    private List<Type> type;



}
