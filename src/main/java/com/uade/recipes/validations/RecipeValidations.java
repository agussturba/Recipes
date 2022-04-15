package com.uade.recipes.validations;

import com.uade.recipes.exceptions.recipeExceptions.PeopleAmountLowerThanOneException;
import com.uade.recipes.exceptions.recipeExceptions.PortionsLowerEqualThanZeroException;
import com.uade.recipes.exceptions.recipeExceptions.RecipeNameContainsNumberException;
import com.uade.recipes.vo.RecipeVo;

import static com.uade.recipes.validations.GeneralValidations.containsNumber;
import static com.uade.recipes.validations.IdValidations.validateDishId;
import static com.uade.recipes.validations.IdValidations.validateUserId;

public class RecipeValidations {
    public static void validateRecipeData(RecipeVo recipeVo) {
        if (containsNumber(recipeVo.getName())) {
            throw new RecipeNameContainsNumberException();
        }
        validateDishId(recipeVo.getDishId());
        validateUserId(recipeVo.getUserId());

        if (recipeVo.getPortions() <= 0) {
            throw new PortionsLowerEqualThanZeroException();
        }
        if (recipeVo.getPeopleAmount() < 1) {
            throw new PeopleAmountLowerThanOneException();
        }
    }
}
