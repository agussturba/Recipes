package com.uade.recipes.exceptions.ExceptionHandlers;

import com.uade.recipes.exceptions.typeExceptions.TypeIdLowerEqualThanZeroException;
import com.uade.recipes.exceptions.typeExceptions.TypeIdNullException;
import com.uade.recipes.exceptions.typeExceptions.TypeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TypeExceptionHandlerAdvice {
    @ExceptionHandler(TypeIdNullException.class)
    public ResponseEntity handleTypeIdNullException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The type id cant be null");
    }

    @ExceptionHandler(TypeIdLowerEqualThanZeroException.class)
    public ResponseEntity handleTypeIdLowerEqualThanZeroException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The type id cant be lower or equal to zero");
    }

    @ExceptionHandler(TypeNotFoundException.class)
    public ResponseEntity handleTypeNotFountException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The Type was not found");
    }
}
