package com.uade.recipes.exceptions.ExceptionHandlers;

import com.uade.recipes.exceptions.recipeRatingExceptions.RatingIsLowerThanZeroException;
import com.uade.recipes.exceptions.recipeRatingExceptions.RatingIsNullException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RecipeRatingExceptionHandlerAdvice {
    @ExceptionHandler(RatingIsNullException.class)
    public ResponseEntity handleRatingIsNullException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The rating cant be null");
    }

    @ExceptionHandler(RatingIsLowerThanZeroException.class)
    public ResponseEntity handleRatingIsLowerThanZeroException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The rating cant be lower than zero");
    }
}
