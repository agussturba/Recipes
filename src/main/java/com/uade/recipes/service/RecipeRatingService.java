package com.uade.recipes.service;

import com.uade.recipes.model.Recipe;
import com.uade.recipes.model.RecipeRating;

import java.util.Optional;

public interface RecipeRatingService {
    Optional<RecipeRating> getRecipeRatingByRecipeId(Integer recipeId);

    Integer getAmountOfRecipeRatingsByRecipeId(Integer recipeId);

    Double getAverageOfRecipeRatingsByRecipeId(Integer recipeId);

    void addRatingToRecipeByRecipeId(Integer recipeId, Double rating);


}
