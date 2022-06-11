package com.uade.recipes.service.ingredient;

import com.uade.recipes.exceptions.ingredientExceptions.IngredientNameContainsNumberException;
import com.uade.recipes.exceptions.ingredientExceptions.IngredientNotFoundException;
import com.uade.recipes.exceptions.ingredientExceptions.IngredientTypeContainsNumberException;
import com.uade.recipes.model.Ingredient;
import com.uade.recipes.vo.IngredientVo;

import java.io.FileNotFoundException;
import java.util.List;

public interface IngredientService {
    List<Ingredient> getAllIngredients();

    Ingredient getIngredientById(Integer ingredientId) throws IngredientNotFoundException;

    Ingredient getIngredientByName(String ingredientName) throws IngredientNotFoundException;

    void saveListIngredients() throws FileNotFoundException;

    Ingredient saveIngredient(IngredientVo ingredientVo) throws IngredientTypeContainsNumberException, IngredientNameContainsNumberException;
}
