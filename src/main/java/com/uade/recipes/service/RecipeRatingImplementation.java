package com.uade.recipes.service;

import com.uade.recipes.model.Recipe;
import com.uade.recipes.model.RecipeRating;
import com.uade.recipes.persistance.RecipeRatingRepository;
import com.uade.recipes.persistance.RecipeRepository;
import org.springframework.stereotype.Service;

@Service
public class RecipeRatingImplementation implements RecipeRatingService {
    private final RecipeRatingRepository recipeRatingRepository;
    private final RecipeRepository recipeRepository;
    private final RecipeService recipeService;

    public RecipeRatingImplementation(RecipeRatingRepository recipeRatingRepository, RecipeRepository recipeRepository, RecipeService recipeService) {
        this.recipeRatingRepository = recipeRatingRepository;
        this.recipeRepository = recipeRepository;
        this.recipeService = recipeService;
    }

    @Override
    public RecipeRating getRecipeRatingByRecipeId(Integer recipeId) {
        Recipe recipe = recipeService.getRecipeById(recipeId);
        return recipeRatingRepository.findByRecipe(recipe);    }

    @Override
    public Integer getAmountOfRatingsByRecipeId(Integer recipeId) {
       RecipeRating recipeRating = this.getRecipeRatingByRecipeId(recipeId);
       return recipeRating.getNumberOfRatings();
    }

    @Override
    public Double getAverageOfRecipeRatingsByRecipeId(Integer recipeId) {
        RecipeRating recipeRating = this.getRecipeRatingByRecipeId(recipeId);
        return recipeRating.getAverageRating();
    }

    @Override
    public void addRatingToRecipeByRecipeId(Integer recipeId, Double rating) {
        RecipeRating recipeRating = this.getRecipeRatingByRecipeId(recipeId);
        recipeRating.addRating(rating);
        recipeRatingRepository.save(recipeRating);
    }
}
