package com.uade.recipes.service.recipeRating;

import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.exceptions.recipeRatingExceptions.RatingIsLowerThanZeroException;
import com.uade.recipes.exceptions.recipeRatingExceptions.RatingIsNullException;
import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;
import com.uade.recipes.model.RecipeRating;
import com.uade.recipes.vo.RecipeRatingVo;

public interface RecipeRatingService {
    RecipeRating getRecipeRatingByRecipeId(Integer recipeId) throws RecipeNotFoundException;

    Integer getAmountOfRatingsByRecipeId(Integer recipeId) throws RecipeNotFoundException;

    Double getAverageOfRecipeRatingsByRecipeId(Integer recipeId) throws RecipeNotFoundException;

    RecipeRating saveOrUpdateRecipeRating(RecipeRatingVo recipeRatingVo) throws UserNotFoundException, RecipeNotFoundException, RatingIsLowerThanZeroException, RatingIsNullException;

}
