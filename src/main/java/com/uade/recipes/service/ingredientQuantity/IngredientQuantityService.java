package com.uade.recipes.service.ingredientQuantity;

import com.uade.recipes.exceptions.ingredientExceptions.CannotDivideTheIngredientException;
import com.uade.recipes.exceptions.ingredientExceptions.IngredientNotFoundException;
import com.uade.recipes.exceptions.ingredientQuantityExceptions.IngredientQuantityNotFoundException;
import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.model.Ingredient;
import com.uade.recipes.model.IngredientQuantity;
import com.uade.recipes.vo.IngredientQuantityVo;

import java.util.List;

public interface IngredientQuantityService {
    List<IngredientQuantity> getAllIngredientQuantity();

    List<IngredientQuantity> getIngredientQuantityByRecipeId(Integer recipeId) throws RecipeNotFoundException;

    List<IngredientQuantity> getConvertedIngredientQuantityListByRecipeIdAndConversionFactor(Integer recipeId, Double conversionFactor) throws RecipeNotFoundException, CannotDivideTheIngredientException, IngredientNotFoundException;

    IngredientQuantity getIngredientQuantityById(Integer ingredientQuantityId) throws IngredientQuantityNotFoundException;

    List<IngredientQuantity> getIngredientQuantityByIngredientId(Integer ingredientId) throws IngredientNotFoundException;

    IngredientQuantity getIngredientQuantityByIngredientAndQuantity(Ingredient ingredient, Double quantity) throws IngredientQuantityNotFoundException;

    IngredientQuantityVo convertIngredientQuantityUnitByTargetUnitId(IngredientQuantityVo ingredientQuantityVo, Integer targetUnitId);

    IngredientQuantity saveOrUpdateIngredientQuantity(IngredientQuantityVo ingredientQuantityVo) throws IngredientNotFoundException, IngredientQuantityNotFoundException, RecipeNotFoundException, CannotDivideTheIngredientException;

    IngredientQuantity getIngredientQuantityByIngredientIdAndRecipeId(Integer ingredientId, Integer recipeId) throws IngredientNotFoundException, RecipeNotFoundException;
}
