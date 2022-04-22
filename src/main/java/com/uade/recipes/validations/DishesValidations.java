package com.uade.recipes.validations;

import com.uade.recipes.exceptions.dishExceptions.DishNameContainsNumberException;
import com.uade.recipes.exceptions.dishExceptions.DishTypeContainsNumberException;
import com.uade.recipes.vo.DishVo;

public class DishesValidations {
    public static void validateDishData(DishVo dishVo) throws DishNameContainsNumberException, DishTypeContainsNumberException {
        if (GeneralValidations.containsNumber(dishVo.getName())) {
            throw new DishNameContainsNumberException();
        }
        IdValidations.validateTypeId(dishVo.getTypeId());

    }
}
