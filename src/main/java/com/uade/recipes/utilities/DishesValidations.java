package com.uade.recipes.utilities;

import com.uade.recipes.exceptions.dishExceptions.DishNameContainsNumberException;
import com.uade.recipes.exceptions.dishExceptions.DishTypeContainsNumberException;
import com.uade.recipes.vo.DishVo;

import static com.uade.recipes.utilities.IngredientsValidations.containsNumber;

public class DishesValidations {
    public static void validateDishData(DishVo dishVo){
        if (containsNumber(dishVo.getName())){
            throw new DishNameContainsNumberException();
        }
        if (containsNumber(dishVo.getType())){
            throw new DishTypeContainsNumberException();
        }
    }
}
