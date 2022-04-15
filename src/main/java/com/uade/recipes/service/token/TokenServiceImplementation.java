package com.uade.recipes.service.token;

import com.uade.recipes.exceptions.tokenExceptions.TokenNotFoundException;
import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;
import com.uade.recipes.model.Token;
import com.uade.recipes.model.User;
import com.uade.recipes.persistance.TokenRepository;
import com.uade.recipes.service.user.UserService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

@Service
public class TokenServiceImplementation implements TokenService {
    private final TokenRepository tokenRepository;
    private final UserService userService;

    public TokenServiceImplementation(TokenRepository tokenRepository, UserService userService) {
        this.tokenRepository = tokenRepository;
        this.userService = userService;
    }

    @Override
    //TODO CAMBIAR VALIDACION
    public boolean isTokenValid(Integer token, Integer userId) throws UserNotFoundException {
        User user = userService.getUserById(userId);
        try {
            Token tokenElem = tokenRepository.findByUserAndTimestampGreaterThanEqualAndTimestampLessThanEqual(user, Timestamp.valueOf(LocalDateTime.now().minusDays(1)), Timestamp.from(Instant.now())).orElseThrow(TokenNotFoundException::new);
            return true;
        } catch (TokenNotFoundException e) {
            return false;
        }

    }

    @Override
    public Integer generateToken(Integer userId) throws UserNotFoundException {
        User user = userService.getUserById(userId);
        Token token = new Token(user);
        tokenRepository.save(token);
        return token.getCode();
    }
}
