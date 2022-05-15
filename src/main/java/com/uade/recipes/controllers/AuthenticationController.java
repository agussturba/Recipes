package com.uade.recipes.controllers;

import com.uade.recipes.exceptions.tokenExceptions.TokenCantBeGeneratedException;
import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;
import com.uade.recipes.model.User;
import com.uade.recipes.service.token.TokenService;
import com.uade.recipes.service.user.UserService;
import com.uade.recipes.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @ApiOperation(value = "Obtener un usuario en base a su email y contraseña", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Se encontró al usuario exitosamente"),
            @ApiResponse(code = 404, message = "Usuario no encontrado"),

    })
    public ResponseEntity<UserVo> login(@RequestParam String email, @RequestParam String password) throws UserNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByEmailAndPassword(email, password).toVO());
    }

    @GetMapping("/token")
    @ApiOperation(value = "Genera y envía el token para continuar el proceso de recuperación de contraseña")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "El token fue generado y enviado con éxito" ),
            @ApiResponse(code = 404, message = "El usuario no fue encontrado"),
            @ApiResponse(code = 422, message = "El token no pudo ser generado")

    })
    public ResponseEntity<Integer> getCode(@RequestParam Integer userId) throws UserNotFoundException, TokenCantBeGeneratedException {
        return ResponseEntity.status(HttpStatus.OK).body(tokenService.generateToken(userId));
    }

    @GetMapping("/check/{token}")
    @ApiOperation(value = "Controla que el token ingresado sea válido")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "El token ingresado es válido"),
            @ApiResponse(code = 404, message = "El usuario no fue encontrado")
    })
    public ResponseEntity<Boolean> isValid(@PathVariable Integer token, @RequestParam Integer userId) throws UserNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(tokenService.isTokenValid(token, userId));
    }

    @PostMapping("/password/restore")
    @ApiOperation(value = "Permite cambiar la contraseña de un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La contraseña fue cambiada con éxito"),
            @ApiResponse(code = 404, message = "El usuario no fue encontrado")
    })
    public ResponseEntity restorePassword(@RequestParam String email, @RequestParam String password) throws UserNotFoundException {
        userService.changePassword(email, password);
        return new ResponseEntity(HttpStatus.OK);
    }
}
