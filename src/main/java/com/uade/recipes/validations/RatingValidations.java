package com.uade.recipes.validations;

import com.uade.recipes.exceptions.recipeRatingExceptions.RatingIsLowerThanZeroException;
import com.uade.recipes.exceptions.recipeRatingExceptions.RatingIsNullException;
import com.uade.recipes.vo.RecipeRatingVo;

import static com.uade.recipes.validations.IdValidations.validateRecipeId;
import static com.uade.recipes.validations.IdValidations.validateUserId;

public class RatingValidations {
    public static void validateRatingData(RecipeRatingVo recipeRatingVo) throws RatingIsLowerThanZeroException, RatingIsNullException {
        if(recipeRatingVo.getRating()==null){
            throw new RatingIsNullException();
        }
        if (recipeRatingVo.getRating()<0){
            throw new RatingIsLowerThanZeroException();
        }
        validateRecipeId(recipeRatingVo.getRecipeId());
        validateUserId(recipeRatingVo.getUserId());
    }
}
