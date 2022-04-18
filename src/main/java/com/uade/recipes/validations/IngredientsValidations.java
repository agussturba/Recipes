package com.uade.recipes.validations;

import com.uade.recipes.exceptions.ingredientExceptions.IngredientNameContainsNumberException;
import com.uade.recipes.vo.IngredientVo;

import static com.uade.recipes.validations.IdValidations.validateTypeId;

public class IngredientsValidations {

    public static void validateIngredientData(IngredientVo ingredientVo) throws IngredientNameContainsNumberException {

        if (GeneralValidations.containsNumber(ingredientVo.getName())){
            throw new IngredientNameContainsNumberException();
        }
        validateTypeId(ingredientVo.getTypeId());
    }
}
