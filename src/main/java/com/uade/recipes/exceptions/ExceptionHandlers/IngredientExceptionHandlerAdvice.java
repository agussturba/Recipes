package com.uade.recipes.exceptions.ExceptionHandlers;

import com.uade.recipes.exceptions.ingredientExceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class IngredientExceptionHandlerAdvice {
    @ExceptionHandler(IngredientNotFoundException.class)
    public ResponseEntity handleIngredientNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The Ingredient was not found");
    }

    @ExceptionHandler(IngredientIdNullException.class)
    public ResponseEntity handleIngredientIdNullException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The ingredient id cant be null");
    }

    @ExceptionHandler(IngredientIdLowerEqualThanZeroException.class)
    public ResponseEntity handleIngredientIdLowerEqualThanZeroException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The ingredient id cant be lower or equal to zero");
    }
    @ExceptionHandler(IngredientNameContainsNumberException.class)
    public ResponseEntity handleIngredientNameContainsNumberException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The ingredient name cant contain numbers");
    }

    @ExceptionHandler(IngredientTypeContainsNumberException.class)
    public ResponseEntity handleIngredientTypeContainsNumberException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The ingredient type cant contain numbers");
    }
    @ExceptionHandler(IngredientNameExistsException.class)
    public ResponseEntity handleIngredientNameExistsException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The ingredient name already exists");
    }
    @ExceptionHandler(CannotDivideTheIngredientException.class)
    public ResponseEntity handleCannotDivideTheIngredientException(){
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("The ingredient cannot be divided to have a fractional amount");
    }
}
