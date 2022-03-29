package com.uade.recipes.exceptions;

import com.uade.recipes.exceptions.ingredientExceptions.IngredientNameContainsNumberException;
import com.uade.recipes.exceptions.ingredientExceptions.IngredientNotFoundException;
import com.uade.recipes.exceptions.ingredientExceptions.IngredientTypeContainsNumberException;
import com.uade.recipes.exceptions.userExceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity handleClientNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User was not found");
    }

    @ExceptionHandler(IngredientNotFoundException.class)
    public ResponseEntity handleIngredientNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ingredient was not found");
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

}
