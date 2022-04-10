package com.uade.recipes.validations;

import com.uade.recipes.exceptions.ingredientExceptions.IngredientNameContainsNumberException;
import com.uade.recipes.exceptions.ingredientExceptions.IngredientTypeContainsNumberException;
import com.uade.recipes.vo.IngredientVo;

public class IngredientsValidations {

    public static void validateIngredientData(IngredientVo ingredientVo) throws IngredientNameContainsNumberException, IngredientTypeContainsNumberException {
        if (GeneralValidations.containsNumber(ingredientVo.getName())){
            throw new IngredientNameContainsNumberException();
        }
       /* if (GeneralValidations.containsNumber(ingredientVo.getType())){
            throw new IngredientTypeContainsNumberException();
        }*/
    }
}
