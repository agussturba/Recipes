package com.uade.recipes.exceptions.ExceptionHandlers;

import com.uade.recipes.exceptions.userExceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandlerAdvice {

    @ExceptionHandler(UserIdNullException.class)
    public ResponseEntity handleUserIdNullException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The user id cant be null");
    }

    @ExceptionHandler(UserIdLowerEqualThanZeroException.class)
    public ResponseEntity handleUserIdLowerEqualThanZeroException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The user id cant be lower or equal to zero");
    }
    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity handleEmailExistsException() {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("There is already a user with that email");
    }


    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity handleInvalidEmailException() {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("The mail has an invalid forma");
    }

    @ExceptionHandler(UserNameExistsException.class)
    public ResponseEntity handleUserNameExistsException() {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("There is already a user with that username");
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity handleInvalidPasswordException() {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("The password is invalid");
    }

    @ExceptionHandler(InvalidRoleException.class)
    public ResponseEntity handleInvalidRoleException() {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("The role is invalid");
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity handleUserNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The User was not found");
    }

    @ExceptionHandler(RegistrationProcessIncompleteException.class)
    public ResponseEntity handleRegistrationProcessIncompleteException() {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("The registration process for the given user is incomplete");
    }
}
