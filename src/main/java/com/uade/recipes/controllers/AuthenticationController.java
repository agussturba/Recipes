package com.uade.recipes.controllers;

import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;
import com.uade.recipes.model.User;
import com.uade.recipes.service.token.TokenService;
import com.uade.recipes.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final UserService userService;
    private final TokenService tokenService;

    public AuthenticationController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @GetMapping("/login")
    @ApiOperation(value = "Get a user by his email and password", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "Successfully retrieved the user by his username and password"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "User not Found"),

    })
    public ResponseEntity<User> login(@RequestParam String email, @RequestParam String password) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByEmailAndPassword(email, password));
    }

    @GetMapping("/test")
    public ResponseEntity<Integer> getCode(@RequestParam Integer userId) throws UserNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(tokenService.generateToken(userId));
    }

    @GetMapping("/testValidity")
    public ResponseEntity<Boolean> isValid(@RequestParam Integer code, @RequestParam Integer userId) throws UserNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(tokenService.isTokenValid(code, userId));
    }

}
