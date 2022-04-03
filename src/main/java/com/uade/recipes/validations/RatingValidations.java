package com.uade.recipes.validations;

import com.uade.recipes.exceptions.recipeRatingExceptions.RatingIsLowerThanZeroException;
import com.uade.recipes.exceptions.recipeRatingExceptions.RatingIsNullException;

public class RatingValidations {
    public static void validateRating(Double rating){
        if(rating==null){
            throw new RatingIsNullException();
        }
        if (rating<0){
            throw new RatingIsLowerThanZeroException();
        }
    }
}
