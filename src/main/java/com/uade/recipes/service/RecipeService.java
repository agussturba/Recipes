package com.uade.recipes.service;

import com.uade.recipes.model.Dish;
import com.uade.recipes.model.Recipe;
import com.uade.recipes.model.User;
import com.uade.recipes.vo.DishVo;
import com.uade.recipes.vo.RecipeVo;

import java.util.List;

public interface RecipeService {
    List<Recipe> getAllRecipes();

    Recipe getRecipeById(Integer recipeId);

    Recipe getRecipeByName(String recipeName);

    List<Recipe> getRecipeByHavingGreaterRating(Double rating);

    List<Recipe> getRecipesByUserId(Integer userId);

    List<Recipe> getRecipesByDishId(Integer dishId);

    List<Recipe> getRecipeByType(String type);

    Recipe saveOrUpdateRecipe(RecipeVo RecipeVo);
}
