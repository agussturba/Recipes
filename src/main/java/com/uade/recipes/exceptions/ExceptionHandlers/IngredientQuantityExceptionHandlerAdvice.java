package com.uade.recipes.exceptions.ExceptionHandlers;

import com.uade.recipes.exceptions.ingredientQuantityExceptions.IngredientQuantityNotFoundException;
import com.uade.recipes.exceptions.ingredientQuantityExceptions.QuantityEqualOrLessThanZeroException;
import com.uade.recipes.exceptions.ingredientQuantityExceptions.UnacceptableQuantityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class IngredientQuantityExceptionHandlerAdvice {
    @ExceptionHandler(IngredientQuantityNotFoundException.class)
    public ResponseEntity handleIngredientQuantityNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The Ingredient-Quantity was not found");
    }

    @ExceptionHandler(QuantityEqualOrLessThanZeroException.class)
    public ResponseEntity handleQuantityEqualOrLessThanZeroException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The quantity cant be lower or equal to zero");
    }
    @ExceptionHandler(UnacceptableQuantityException.class)
    public ResponseEntity handleUnacceptableQuantityException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The quantity indicated is not possible for that ingredient");
    }

}
