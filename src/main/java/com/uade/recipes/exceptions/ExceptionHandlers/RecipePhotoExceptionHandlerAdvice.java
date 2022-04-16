package com.uade.recipes.exceptions.ExceptionHandlers;

import com.uade.recipes.exceptions.recipePhotoExceptions.RecipePhotoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RecipePhotoExceptionHandlerAdvice {
    @ExceptionHandler(RecipePhotoNotFoundException.class)
    public ResponseEntity handleRecipePhotoNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The Recipe photo was not found");
    }

}
