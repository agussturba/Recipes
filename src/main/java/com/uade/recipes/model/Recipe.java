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
    @ElementCollection
    private List<String> photos;
    @ManyToMany
    private List<IngredientQuantity> ingredientQuantityList;
    @OneToOne
    private Dish dish;
    @OneToOne
    private User user;
    private Integer peopleAmount;
    @OneToMany
    private Type type;


}
