package com.uade.recipes.service.dish;

import com.uade.recipes.model.Dish;
import com.uade.recipes.model.Ingredient;
import com.uade.recipes.vo.DishVo;

import java.util.List;

public interface DishService {
    List<Dish> getAllDishes();

    Dish getDishById(Integer ingredientId);

    Dish getDishByName(String dishName);

    List<Dish> getDishesByType(String dishType);

    Dish saveOrUpdateDish(DishVo DishVo);
}
