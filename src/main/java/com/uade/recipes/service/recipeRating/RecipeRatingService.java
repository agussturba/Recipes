package com.uade.recipes.service.recipeRating;

import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.exceptions.recipeRatingExceptions.RatingIsLowerThanZeroException;
import com.uade.recipes.exceptions.recipeRatingExceptions.RatingIsNullException;
import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;
import com.uade.recipes.model.RecipeRating;
import com.uade.recipes.vo.RecipeRatingVo;

import java.util.List;

public interface RecipeRatingService {
    List<RecipeRating> getRecipeRatingByRecipeId(Integer recipeId) throws RecipeNotFoundException;

    RecipeRating getRecipeRatingByRecipeIdAndUserId(Integer recipeId,Integer userId) throws RecipeNotFoundException, UserNotFoundException;

    Integer getAmountOfRatingsByRecipeId(Integer recipeId) throws RecipeNotFoundException;

    Double getAverageOfRecipeRatingsByRecipeId(Integer recipeId) throws RecipeNotFoundException;

    RecipeRating updateRecipeRating(RecipeRatingVo recipeRatingVo) throws UserNotFoundException, RecipeNotFoundException, RatingIsLowerThanZeroException, RatingIsNullException;

    Double getAverageOfRecipeRatingsByUser(Integer userId) throws UserNotFoundException, RecipeNotFoundException;

    RecipeRating saveRecipeRating(RecipeRatingVo recipeRatingVo) throws UserNotFoundException, RecipeNotFoundException;
}
