package com.uade.recipes.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Document
public class RecipeRating {
    @Transient
    public static final String SEQUENCE_NAME = "recipeRating_sequence";
    @Id
    private Integer id;
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