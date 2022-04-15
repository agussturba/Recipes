package com.uade.recipes.service.ingredient;

import com.uade.recipes.exceptions.ingredientExceptions.IngredientNameContainsNumberException;
import com.uade.recipes.exceptions.ingredientExceptions.IngredientNotFoundException;
import com.uade.recipes.exceptions.ingredientExceptions.IngredientTypeContainsNumberException;
import com.uade.recipes.model.Ingredient;
import com.uade.recipes.model.Type;
import com.uade.recipes.vo.IngredientVo;

import java.util.List;

public interface IngredientService {
    List<Ingredient> getAllIngredients();

    Ingredient getIngredientById(Integer ingredientId) throws IngredientNotFoundException;

    Ingredient getIngredientByName(String ingredientName) throws IngredientNotFoundException;

    List<Ingredient> getIngredientsByTypeId(Integer ingredientTypeId);

    Ingredient saveOrUpdateIngredient(IngredientVo ingredientVo) throws IngredientTypeContainsNumberException, IngredientNameContainsNumberException;
}
