package com.uade.recipes.exceptions.ExceptionHandlers;

import com.uade.recipes.exceptions.recipeExceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RecipeExceptionHandlerAdvice {
    @ExceptionHandler(PeopleAmountLowerThanOneException.class)
    public ResponseEntity handlePeopleAmountLowerThanOneException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The people amount cant be lower to 1");
    }

    @ExceptionHandler(PortionsLowerEqualThanZeroException.class)
    public ResponseEntity handlePortionsLowerEqualThanZeroException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The portions cant be lower or equal to zero");
    }

    @ExceptionHandler(RecipeNameContainsNumberException.class)
    public ResponseEntity handleRecipeNameContainsNumberException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The recipe name cant have a number ");
    }

    @ExceptionHandler(RecipeIdNullException.class)
    public ResponseEntity handleRecipeIdNullException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The recipe id cant be null");
    }

    @ExceptionHandler(RecipeIdLowerOrEqualThanOneException.class)
    public ResponseEntity handleRecipeIdLowerOrEqualThanOneException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The recipe id cant be lower or equal to zero");
    }

    @ExceptionHandler(RecipeNotFoundException.class)
    public ResponseEntity handleRecipeNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The Recipe was not found");
    }
}
