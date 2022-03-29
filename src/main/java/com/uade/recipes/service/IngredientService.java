package com.uade.recipes.service;
//May not be needed

import com.uade.recipes.model.Ingredient;
import com.uade.recipes.vo.IngredientVo;

import java.util.List;

public interface IngredientService {
    List<Ingredient> getAllIngredients();

    Ingredient getIngredientById(Integer ingredientId);

    Ingredient getIngredientByName(String ingredientName);

    List<Ingredient> getIngredientsByType(String ingredientType);

    Ingredient saveOrUpdateIngredient(IngredientVo ingredientVo);
}
