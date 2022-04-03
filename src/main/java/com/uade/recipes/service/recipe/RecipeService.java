package com.uade.recipes.service.recipe;

import com.uade.recipes.model.Recipe;
import com.uade.recipes.vo.RecipeVo;

import java.util.List;

public interface RecipeService {
    List<Recipe> getAllRecipes();

    Recipe getRecipeById(Integer recipeId);

    Recipe getRecipeByName(String recipeName);

    List<Recipe> getRecipesByUserId(Integer userId);

    List<Recipe> getRecipesByLabels(List<String> labels);

    List<Recipe> getRecipesByUserIdAndDishId(Integer userId, Integer dishId);

    List<Recipe> getRecipesByDishId(Integer dishId);

    Recipe saveOrUpdateRecipe(RecipeVo RecipeVo);
}