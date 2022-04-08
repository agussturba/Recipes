package com.uade.recipes.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
public class RecipeRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @OneToOne
    private Recipe recipe;
    private Double rating;
    @OneToOne
    private User user;
    private String comments;

    public RecipeRating(Recipe recipe) {
        this.recipe = recipe;
    }

}