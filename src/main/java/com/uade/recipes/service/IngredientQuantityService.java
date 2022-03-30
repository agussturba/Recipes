package com.uade.recipes.service;

import com.uade.recipes.model.Dish;
import com.uade.recipes.model.Ingredient;
import com.uade.recipes.model.IngredientQuantity;
import com.uade.recipes.vo.DishVo;
import com.uade.recipes.vo.IngredientQuantityVo;

import java.util.List;

public interface IngredientQuantityService {
    List<IngredientQuantity> getAllIngredientQuantity();

    IngredientQuantity getIngredientQuantityById(Integer ingredientQuantityId);

    List<IngredientQuantity> getIngredientQuantityByIngredientId(Integer ingredientId);

    IngredientQuantity getIngredientQuantityByIngredientAndQuantity(Ingredient ingredient,Double quantity);

    IngredientQuantity saveOrUpdateIngredientQuantity(IngredientQuantityVo ingredientQuantityVo);
}
