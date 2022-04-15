package com.uade.recipes.exceptions.ExceptionHandlers;

import com.uade.recipes.exceptions.userPhotoExceptions.UserPhotoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserPhotoExceptionHandlerAdvice {
    @ExceptionHandler(UserPhotoNotFoundException.class)
    public ResponseEntity handleUserPhotoNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The photo of the user was not found");
    }
}
