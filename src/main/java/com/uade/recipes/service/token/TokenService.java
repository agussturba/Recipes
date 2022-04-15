package com.uade.recipes.service.token;

import com.uade.recipes.exceptions.tokenExceptions.TokenCantBeGeneratedException;
import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;

public interface TokenService {

    boolean isTokenValid(Integer token, Integer userId) throws UserNotFoundException;

    Integer generateToken(Integer userId) throws UserNotFoundException, TokenCantBeGeneratedException;

    void sendTokenEmail(String email, int token);

    boolean canGenerateToken(Integer userId) throws UserNotFoundException;
}
