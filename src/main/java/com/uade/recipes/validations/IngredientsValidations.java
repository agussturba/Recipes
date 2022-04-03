package com.uade.recipes.validations;

import com.uade.recipes.exceptions.ingredientExceptions.IngredientNameContainsNumberException;
import com.uade.recipes.exceptions.ingredientExceptions.IngredientTypeContainsNumberException;
import com.uade.recipes.vo.IngredientVo;

import static com.uade.recipes.validations.GeneralValidations.containsNumber;

public class IngredientsValidations {

    public static void validateIngredientData(IngredientVo ingredientVo){
        if (containsNumber(ingredientVo.getName())){
            throw new IngredientNameContainsNumberException();
        }
        if (containsNumber(ingredientVo.getType())){
            throw new IngredientTypeContainsNumberException();
        }
    }
}
