package com.uade.recipes.exceptions.tokenExceptions;

public class TokenCantBeGeneratedException extends Exception{
    String message;
    public TokenCantBeGeneratedException(String message) {
        super(message);
    }
}
