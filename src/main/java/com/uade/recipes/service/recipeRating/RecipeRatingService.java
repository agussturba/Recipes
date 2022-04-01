package com.uade.recipes.service.recipeRating;

import com.uade.recipes.model.RecipeRating;

public interface RecipeRatingService {
    RecipeRating getRecipeRatingByRecipeId(Integer recipeId);

    Integer getAmountOfRatingsByRecipeId(Integer recipeId);

    Double getAverageOfRecipeRatingsByRecipeId(Integer recipeId);

    void addRatingToRecipeByRecipeId(Integer recipeId, Double rating);


}
