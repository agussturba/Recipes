package com.uade.recipes.exceptions.ExceptionHandlers;

import com.uade.recipes.exceptions.dishExceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DishExceptionHandlerAdvice {
    @ExceptionHandler(DishIdLowerEqualThanZeroException.class)
    public ResponseEntity handleDishIdLowerEqualThanZeroException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The dish id cant be lower or equal than zero");
    }

    @ExceptionHandler(DishIdNullException.class)
    public ResponseEntity handleDishIdNullException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The dish id cant be null");
    }

    @ExceptionHandler(DishNotFoundException.class)
    public ResponseEntity handleDishNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The Dish was not found");
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
