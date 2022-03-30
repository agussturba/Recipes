package com.uade.recipes.model;

import javax.persistence.*;
import java.util.*;

@Table(name = "recipe_rating")
@Entity
public class RecipeRating {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @OneToOne
    private Recipe recipe;
    private List<Double> ratings;

    public RecipeRating(Recipe recipe) {
        ratings = new ArrayList<>();
        this.recipe = recipe;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public List<Double> getRatings() {
        return ratings;
    }

    public void setRatings(List<Double> ratings) {
        this.ratings = ratings;
    }
    public void addRating(Double rating){
        this.ratings.add(rating);
    }
    public int getNumberOfRatings(){
        return ratings.size();
    }
    public Double getAverageRating(){
        return ratings.stream().mapToDouble(a -> a).average().orElse(0);
    }
}