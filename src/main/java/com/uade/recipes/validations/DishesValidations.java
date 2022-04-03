package com.uade.recipes.validations;

import com.uade.recipes.exceptions.dishExceptions.DishNameContainsNumberException;
import com.uade.recipes.exceptions.dishExceptions.DishTypeContainsNumberException;
import com.uade.recipes.vo.DishVo;

import static com.uade.recipes.validations.GeneralValidations.containsNumber;

public class DishesValidations {
    public static void validateDishData(DishVo dishVo){
        if (containsNumber(dishVo.getName())){
            throw new DishNameContainsNumberException();
        }
        for (String label: dishVo.getLabels()) {
            if (containsNumber(label)){
                throw new DishTypeContainsNumberException();
            }
        }

    }
}
