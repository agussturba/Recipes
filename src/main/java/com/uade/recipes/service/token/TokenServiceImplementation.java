package com.uade.recipes.service.token;

import com.uade.recipes.exceptions.tokenExceptions.TokenCantBeGeneratedException;
import com.uade.recipes.exceptions.tokenExceptions.TokenNotFoundException;
import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;
import com.uade.recipes.model.Token;
import com.uade.recipes.model.User;
import com.uade.recipes.persistance.TokenRepository;
import com.uade.recipes.service.email.EmailSenderService;
import com.uade.recipes.service.user.UserService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

@Service
public class TokenServiceImplementation implements TokenService {
    private final TokenRepository tokenRepository;
    private final UserService userService;
    private final EmailSenderService emailSenderService;

    public TokenServiceImplementation(TokenRepository tokenRepository, UserService userService, EmailSenderService emailSenderService) {
        this.tokenRepository = tokenRepository;
        this.userService = userService;
        this.emailSenderService = emailSenderService;
    }

    @Override
    public boolean isTokenValid(Integer token, Integer userId) throws UserNotFoundException {
        User user = userService.getUserById(userId);
        try {
            tokenRepository.findByUserAndCodeAndTimestampGreaterThanEqualAndTimestampLessThanEqual(user, token, Timestamp.valueOf(LocalDateTime.now().minusDays(1)),
                    Timestamp.from(Instant.now())).orElseThrow(TokenNotFoundException::new);
            return true;
        } catch (TokenNotFoundException e) {
            return false;
        }
    }


    @Override
    public Integer generateToken(Integer userId) throws UserNotFoundException, TokenCantBeGeneratedException {
        User user = userService.getUserById(userId);
        if (canGenerateToken(userId)) {
            Token token = new Token(user);
            tokenRepository.save(token);
            sendTokenEmail(user.getEmail(), token.getCode());
            return token.getCode();
        }
        else {
            throw new TokenCantBeGeneratedException();
        }
    }

    @Override
    public void sendTokenEmail(String email, int token){
        emailSenderService.sendSimpleEmail(email, "Tu código de Seguridad es: " + token + ". Válido por 24hs.", "Recupero de Contraseña - Código de Seguridad");
    }

    @Override
    public boolean canGenerateToken(Integer userId) throws UserNotFoundException {
        User user = userService.getUserById(userId);
        try {
            tokenRepository.findFirstByUserAndTimestampGreaterThanEqualAndTimestampLessThanEqualOrderByTimestampDesc(user, Timestamp.valueOf(LocalDateTime.now().minusDays(1)),
                    Timestamp.from(Instant.now())).orElseThrow(TokenNotFoundException::new);
            return false;
        } catch (TokenNotFoundException e) {
            return true;
        }
    }
}
