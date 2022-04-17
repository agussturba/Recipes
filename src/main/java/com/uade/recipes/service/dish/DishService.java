package com.uade.recipes.service.dish;

import com.uade.recipes.exceptions.dishExceptions.DishNameContainsNumberException;
import com.uade.recipes.exceptions.dishExceptions.DishNotFoundException;
import com.uade.recipes.exceptions.dishExceptions.DishTypeContainsNumberException;
import com.uade.recipes.model.Dish;
import com.uade.recipes.vo.DishVo;

import java.util.List;

public interface DishService {
    List<Dish> getAllDishes();

    Dish getDishById(Integer ingredientId) throws DishNotFoundException;

    Dish getDishByName(String dishName) throws DishNotFoundException;

    List<Dish> getDishesByTypeId(List<Integer> typeIdList);

    Dish saveOrUpdateDish(DishVo DishVo) throws DishNameContainsNumberException, DishTypeContainsNumberException;
}
