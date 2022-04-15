package com.uade.recipes.validations;

import com.uade.recipes.exceptions.ingredientQuantityExceptions.QuantityEqualOrLessThanZeroException;
import com.uade.recipes.vo.IngredientQuantityVo;

import static com.uade.recipes.validations.IdValidations.*;

public class IngredientQuantityValidations {
    public static void validateIngredientQuantityData(IngredientQuantityVo ingredientQuantityVo){
         validateRecipeId(ingredientQuantityVo.getRecipeId());
         validateUnitId(ingredientQuantityVo.getUnitId());
         validateIngredientId(ingredientQuantityVo.getIngredientId());
         if (ingredientQuantityVo.getQuantity() <=0){
             throw new QuantityEqualOrLessThanZeroException();
         }
    }
}
