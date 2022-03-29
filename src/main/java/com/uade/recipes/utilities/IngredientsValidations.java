package com.uade.recipes.utilities;

import com.uade.recipes.exceptions.ingredientExceptions.IngredientNameContainsNumberException;
import com.uade.recipes.exceptions.ingredientExceptions.IngredientTypeContainsNumberException;
import com.uade.recipes.vo.IngredientVo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IngredientsValidations {
    private static boolean containsNumber(String string) {
        Pattern pattern = Pattern.compile("(.)*(\\d)(.)*");
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }
    public static void validateIngredientData(IngredientVo ingredientVo){
        if (containsNumber(ingredientVo.getName())){
            throw new IngredientNameContainsNumberException();
        }
        if (containsNumber(ingredientVo.getType())){
            throw new IngredientTypeContainsNumberException();
        }
    }
}
