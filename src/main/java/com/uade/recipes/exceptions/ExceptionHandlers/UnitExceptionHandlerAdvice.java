package com.uade.recipes.exceptions.ExceptionHandlers;

import com.uade.recipes.exceptions.unitExceptions.UnitIdLowerEqualThanZeroException;
import com.uade.recipes.exceptions.unitExceptions.UnitIdNullException;
import com.uade.recipes.exceptions.unitExceptions.UnitNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UnitExceptionHandlerAdvice {

    @ExceptionHandler(UnitNotFoundException.class)
    public ResponseEntity handleUnitNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The Unit was not found");
    }

    @ExceptionHandler(UnitIdNullException.class)
    public ResponseEntity handleUnitIdNullException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The unit id cant be null");
    }

    @ExceptionHandler(UnitIdLowerEqualThanZeroException.class)
    public ResponseEntity handleUnitIdLowerEqualThanZeroException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The unit id cant be lower or equal to zero");
    }
}
