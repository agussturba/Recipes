package com.uade.recipes.service.ingredientQuantity;

import com.uade.recipes.exceptions.ingredientExceptions.CannotDivideTheIngredientException;
import com.uade.recipes.exceptions.ingredientExceptions.IngredientNotFoundException;
import com.uade.recipes.exceptions.ingredientQuantityExceptions.IngredientQuantityNotFoundException;
import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.model.IngredientQuantity;
import com.uade.recipes.model.Recipe;
import com.uade.recipes.vo.IngredientQuantityVo;

import java.util.List;
import java.util.Set;

public interface IngredientQuantityService {

    List<IngredientQuantity> getIngredientQuantityByRecipeId(Integer recipeId) throws RecipeNotFoundException;

    List<IngredientQuantity> getConvertedIngredientQuantityListByRecipeIdAndConversionFactor(Integer recipeId, Double conversionFactor) throws RecipeNotFoundException, CannotDivideTheIngredientException, IngredientNotFoundException;

    IngredientQuantity getIngredientQuantityById(Integer ingredientQuantityId) throws IngredientQuantityNotFoundException;

    List<IngredientQuantity> getIngredientQuantityByIngredientId(Integer ingredientId) throws IngredientNotFoundException;

    Set<Recipe> getRecipesByIngredientId(Integer ingredientId) throws IngredientNotFoundException;

    IngredientQuantityVo convertIngredientQuantityUnitByTargetUnitId(IngredientQuantityVo ingredientQuantityVo, Integer targetUnitId);

    IngredientQuantity saveOrUpdateIngredientQuantity(IngredientQuantityVo ingredientQuantityVo) throws IngredientNotFoundException, IngredientQuantityNotFoundException, RecipeNotFoundException, CannotDivideTheIngredientException;

    IngredientQuantity getIngredientQuantityByIngredientIdAndRecipeId(Integer ingredientId, Integer recipeId) throws IngredientNotFoundException, RecipeNotFoundException;

    void deleteAllIngredientQuantities(Integer recipeId) throws RecipeNotFoundException;
}
