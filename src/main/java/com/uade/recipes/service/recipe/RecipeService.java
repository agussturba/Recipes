package com.uade.recipes.service.recipe;

import com.uade.recipes.exceptions.dishExceptions.DishNotFoundException;
import com.uade.recipes.exceptions.instructionExceptions.InstructionNotFoundException;
import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;
import com.uade.recipes.model.Recipe;
import com.uade.recipes.model.Type;
import com.uade.recipes.vo.RecipeVo;

import java.util.List;
import java.util.Set;

public interface RecipeService {
    List<Recipe> getAllRecipes();

    Recipe getRecipeById(Integer recipeId) throws RecipeNotFoundException;

    List<Recipe> getRecipeByName(String recipeName);

    List<Recipe> getRecipesByUserId(Integer userId) throws UserNotFoundException;

    Set<Recipe> getRecipesByTypes(List<Type> types);

    List<Recipe> getRecipesByUserIdAndDishId(Integer userId, Integer dishId) throws DishNotFoundException, UserNotFoundException;

    List<Recipe> getRecipesByDishId(Integer dishId) throws UserNotFoundException;

    Recipe saveOrUpdateRecipe(RecipeVo RecipeVo) throws DishNotFoundException, InstructionNotFoundException, UserNotFoundException;
}
