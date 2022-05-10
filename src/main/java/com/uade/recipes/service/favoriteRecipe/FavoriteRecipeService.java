package com.uade.recipes.service.favoriteRecipe;

import com.uade.recipes.exceptions.ingredientExceptions.IngredientNotFoundException;
import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;
import com.uade.recipes.model.FavoriteRecipe;
import io.swagger.models.auth.In;

import java.util.List;

public interface FavoriteRecipeService {
    List<FavoriteRecipe> getAllFavoritesRecipesByUserId(Integer userId) throws UserNotFoundException;

    FavoriteRecipe saveFavoriteRecipe(Integer recipeId, Integer userId) throws IngredientNotFoundException, RecipeNotFoundException, UserNotFoundException;

    void deleteFavoriteRecipeByUserIdAndRecipeId(Integer recipeId, Integer userId) throws IngredientNotFoundException, RecipeNotFoundException, UserNotFoundException;

    void deleteFavoriteRecipeByFavoriteRecipeId(Integer favoriteRecipeId) throws IngredientNotFoundException, RecipeNotFoundException, UserNotFoundException;

}
