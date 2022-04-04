package com.uade.recipes.model;

import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
public class RecipeRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @OneToOne
    private Recipe recipe;
    @ElementCollection
    private List<Double> ratings;

    public RecipeRating(Recipe recipe) {
        this.recipe = recipe;
    }

    public void addRating(Double rating) {
        this.ratings.add(rating);
    }

    public int getNumberOfRatings() {
        return ratings.size();
    }

    public Double getAverageRating() {
        return ratings.stream().mapToDouble(a -> a).average().orElse(0);
    }
}