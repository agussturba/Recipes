package com.uade.recipes.exceptions.ExceptionHandlers;

import com.uade.recipes.exceptions.conversionExceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ConversionExceptionHandlerAdvice {
    @ExceptionHandler(ConversionNotFoundException.class)
    public ResponseEntity handleConversionNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The Conversion was not found");
    }
    @ExceptionHandler(ConversionFactorLessThanZeroException.class)
    public ResponseEntity handleConversionFactorLessThanZeroException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The conversion factor cant be less tan zero");
    }
    @ExceptionHandler(SourceUnitIdLessOrEqualToZeroException.class)
    public ResponseEntity handleSourceUnitIdLessOrEqualToZeroException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The sourceUnit Id cant be lower or equal to zero");
    }
    @ExceptionHandler(TargetUnitIdLessOrEqualToZeroException.class)
    public ResponseEntity handleTargetUnitIdLessOrEqualToZeroException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The targetUnit Id cant be lower or equal to zero");
    }
    @ExceptionHandler(ConversionExistsException.class)
    public ResponseEntity handleConversionExistsException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The conversion has been already created");
    }
}
