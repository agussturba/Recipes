package com.uade.recipes.service.token;

import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;

public interface TokenService {

    boolean isTokenValid(Integer token, Integer userId) throws UserNotFoundException;

    Integer generateToken(Integer userId) throws UserNotFoundException;
}
