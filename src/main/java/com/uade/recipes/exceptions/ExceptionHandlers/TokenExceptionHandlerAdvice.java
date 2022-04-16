package com.uade.recipes.exceptions.ExceptionHandlers;

import com.uade.recipes.exceptions.tokenExceptions.TokenCantBeGeneratedException;
import com.uade.recipes.exceptions.tokenExceptions.TokenNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TokenExceptionHandlerAdvice {
    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity handleTokenNotFountException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The token was not found");
    }

    @ExceptionHandler(TokenCantBeGeneratedException.class)
    public ResponseEntity handleTokenCantBeGeneratedException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is currently another valid token for this user");
    }
}
