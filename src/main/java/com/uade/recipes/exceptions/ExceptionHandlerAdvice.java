package com.uade.recipes.exceptions;

import com.uade.recipes.exceptions.conversionExceptions.ConversionNotFoundException;
import com.uade.recipes.exceptions.dishExceptions.DishNameContainsNumberException;
import com.uade.recipes.exceptions.dishExceptions.DishNotFoundException;
import com.uade.recipes.exceptions.dishExceptions.DishTypeContainsNumberException;
import com.uade.recipes.exceptions.ingredientExceptions.IngredientNameContainsNumberException;
import com.uade.recipes.exceptions.ingredientExceptions.IngredientNotFoundException;
import com.uade.recipes.exceptions.ingredientExceptions.IngredientTypeContainsNumberException;
import com.uade.recipes.exceptions.ingredientQuantityExceptions.IngredientQuantityNotFoundException;
import com.uade.recipes.exceptions.instructionExceptions.InstructionNotFoundException;
import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.exceptions.recipePhotoExceptions.RecipePhotoNotFoundException;
import com.uade.recipes.exceptions.recipeRatingExceptions.RatingIsLowerThanZeroException;
import com.uade.recipes.exceptions.recipeRatingExceptions.RatingIsNullException;
import com.uade.recipes.exceptions.tokenExceptions.TokenNotFoundException;
import com.uade.recipes.exceptions.typeExceptions.TypeNotFountException;
import com.uade.recipes.exceptions.unitExceptions.UnitNotFoundException;
import com.uade.recipes.exceptions.userExceptions.*;
import com.uade.recipes.exceptions.userPhotoExceptions.UserPhotoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity handleClientNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The User was not found");
    }

    @ExceptionHandler(TypeNotFountException.class)
    public ResponseEntity handleTypeNotFountException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The Type was not found");
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity handleTokenNotFountException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The token was not found");
    }

    @ExceptionHandler(ConversionNotFoundException.class)
    public ResponseEntity handleConversionNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The Conversion was not found");
    }

    @ExceptionHandler(UnitNotFoundException.class)
    public ResponseEntity handleUnitNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The Unit was not found");
    }

    @ExceptionHandler(IngredientNotFoundException.class)
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

    @ExceptionHandler(RecipePhotoNotFoundException.class)
    public ResponseEntity handleRecipePhotoNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The Recipe photo was not found");
    }

    @ExceptionHandler(InstructionNotFoundException.class)
    public ResponseEntity handleInstructionNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The Instruction was not found");
    }

    @ExceptionHandler(IngredientQuantityNotFoundException.class)
    public ResponseEntity handleIngredientQuantityNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The Ingredient-Quantity was not found");
    }

    @ExceptionHandler(UserPhotoNotFoundException.class)
    public ResponseEntity handleUserPhotoNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The photo of the user was not found");
    }

    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity handleEmailExistsException() {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("There is already a user with that email");
    }
    @ExceptionHandler(MultimediaNotFoundException.class)
    public ResponseEntity handleMultimediaNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There multimedia was not found");
    }

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity handleInvalidEmailException() {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("The mail has an invalid forma");
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

    @ExceptionHandler(RatingIsNullException.class)
    public ResponseEntity handleRatingIsNullException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The rating cant be null");
    }

    @ExceptionHandler(RatingIsLowerThanZeroException.class)
    public ResponseEntity handleRatingIsLowerThanZeroException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The rating cant be lower than zero");
    }


}
