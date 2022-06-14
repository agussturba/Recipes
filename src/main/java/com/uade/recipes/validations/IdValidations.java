package com.uade.recipes.validations;


import com.uade.recipes.exceptions.ingredientExceptions.IngredientIdLowerEqualThanZeroException;
import com.uade.recipes.exceptions.ingredientExceptions.IngredientIdNullException;
import com.uade.recipes.exceptions.recipeExceptions.RecipeIdLowerOrEqualThanOneException;
import com.uade.recipes.exceptions.recipeExceptions.RecipeIdNullException;
import com.uade.recipes.exceptions.typeExceptions.TypeIdLowerEqualThanZeroException;
import com.uade.recipes.exceptions.typeExceptions.TypeIdNullException;
import com.uade.recipes.exceptions.unitExceptions.UnitIdLowerEqualThanZeroException;
import com.uade.recipes.exceptions.unitExceptions.UnitIdNullException;
import com.uade.recipes.exceptions.userExceptions.UserIdLowerEqualThanZeroException;
import com.uade.recipes.exceptions.userExceptions.UserIdNullException;

public class IdValidations {

    public static void validateRecipeId(Integer recipeId) {
        if (recipeId == null) {
            throw new RecipeIdNullException();
        }
        if (recipeId < 1) {
            throw new RecipeIdLowerOrEqualThanOneException();
        }
    }


    public static void validateTypeId(Integer typeId) {
        if (typeId == null) {
            throw new TypeIdNullException();
        }
        if (typeId <= 0) {
            throw new TypeIdLowerEqualThanZeroException();
        }
    }

    public static void validateUnitId(Integer unitId) {
        if (unitId == null) {
            throw new UnitIdNullException();
        }
        if (unitId <= 0) {
            throw new UnitIdLowerEqualThanZeroException();
        }
    }

    public static void validateIngredientId(Integer ingredientId) {
        if (ingredientId == null) {
            throw new IngredientIdNullException();
        }
        if (ingredientId <= 0) {
            throw new IngredientIdLowerEqualThanZeroException();
        }
    }

    public static void validateUserId(Integer userId) {
        if (userId == null) {
            throw new UserIdNullException();
        }
        if (userId <= 0) {
            throw new UserIdLowerEqualThanZeroException();
        }
    }
}

