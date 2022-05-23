package com.uade.recipes.exceptions.ExceptionHandlers;

import com.uade.recipes.exceptions.multimediaExceptions.MultimediaNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MultimediaHandlerAdvice {
    @ExceptionHandler(MultimediaNotFoundException.class)
    public ResponseEntity handleMultimediaNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There multimedia was not found");
    }

}
