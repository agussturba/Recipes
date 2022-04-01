package com.uade.recipes.service.recipe;

import com.uade.recipes.model.Recipe;
import com.uade.recipes.vo.RecipeVo;

import java.util.List;

public interface RecipeService {
    List<Recipe> getAllRecipes();

    Recipe getRecipeById(Integer recipeId);

    Recipe getRecipeByName(String recipeName);

    List<Recipe> getRecipesByUserId(Integer userId);

    List<Recipe> getRecipesByUserIdAndDishId(Integer userId, Integer dishId);

    List<Recipe> getRecipesByDishIdAndTypeAndUserId(Integer dishId, String type,Integer userId);

    List<Recipe> getRecipesByUserIdAndType(Integer userId, String type);

    List<Recipe> getRecipesByDishIdAndType(Integer dishId, String type);

    List<Recipe> getRecipesByDishId(Integer dishId);

    List<Recipe> getRecipeByType(String type);

    Recipe saveOrUpdateRecipe(RecipeVo RecipeVo);
}
