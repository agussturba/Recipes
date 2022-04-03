package com.uade.recipes.exceptions;

import com.uade.recipes.exceptions.dishExceptions.DishNameContainsNumberException;
import com.uade.recipes.exceptions.dishExceptions.DishNotFoundException;
import com.uade.recipes.exceptions.dishExceptions.DishTypeContainsNumberException;
import com.uade.recipes.exceptions.ingredientExceptions.IngredientNameContainsNumberException;
import com.uade.recipes.exceptions.ingredientExceptions.IngredientNotFoundException;
import com.uade.recipes.exceptions.ingredientExceptions.IngredientTypeContainsNumberException;
import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.exceptions.userExceptions.*;
import com.uade.recipes.model.Dish;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity handleClientNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The User was not found");
    }

    @ExceptionHandler(IngredientNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity handleIngredientNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The Ingredient was not found");
    }
    @ExceptionHandler(DishNotFoundException.class)
    public ResponseEntity handleDishNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The Dish was not found");
    }
    @ExceptionHandler(RecipeNotFoundException.class)
    public ResponseEntity handleRecipeNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The Recipe was not found");
    }

    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity handleEmailExistsException() {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("There is already a user with that email");
    }

    @ExceptionHandler(UserNameExistsException.class)
    public ResponseEntity handleUserNameExistsException() {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("There is already a user with that username");
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity handleInvalidPasswordException() {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("The password is invalid");
    }

    @ExceptionHandler(InvalidRoleException.class)
    public ResponseEntity handleInvalidRoleException() {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("The role is invalid");
    }

    @ExceptionHandler(IngredientNameContainsNumberException.class)
    public ResponseEntity handleIngredientNameContainsNumberException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The ingredient name cant contain numbers");
    }

    @ExceptionHandler(IngredientTypeContainsNumberException.class)
    public ResponseEntity handleIngredientTypeContainsNumberException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The ingredient type cant contain numbers");
    }
    @ExceptionHandler(DishNameContainsNumberException.class)
    public ResponseEntity handleDishNameContainsNumberException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The dish name cant contain numbers");
    }

    @ExceptionHandler(DishTypeContainsNumberException.class)
    public ResponseEntity handleDishTypeContainsNumberException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The dish type cant contain numbers");
    }


}
