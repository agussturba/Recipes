package com.uade.recipes.service.dish;

import com.uade.recipes.exceptions.dishExceptions.DishNameContainsNumberException;
import com.uade.recipes.exceptions.dishExceptions.DishNotFoundException;
import com.uade.recipes.exceptions.dishExceptions.DishTypeContainsNumberException;
import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.model.Dish;
import com.uade.recipes.model.Recipe;
import com.uade.recipes.vo.DishVo;

import java.util.List;

public interface DishService {
    List<Dish> getAllDishes();

    Dish getDishById(Integer ingredientId) throws DishNotFoundException;

    Dish getDishByName(String dishName) throws DishNotFoundException;

    Dish getDishByRecipeId(Integer recipeId) throws RecipeNotFoundException;

    List<Dish> getDishesByTypeIdList(List<Integer> typeIdList);

    void addRecipeToDishByRecipeIdAndDishId(Recipe recipe, Integer dishId);

    Dish saveOrUpdateDish(DishVo DishVo) throws DishNameContainsNumberException, DishTypeContainsNumberException;
}
