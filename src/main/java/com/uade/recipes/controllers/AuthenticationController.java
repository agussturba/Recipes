package com.uade.recipes.controllers;

import com.uade.recipes.exceptions.tokenExceptions.TokenCantBeGeneratedException;
import com.uade.recipes.exceptions.tokenExceptions.TokenNotFoundException;
import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;
import com.uade.recipes.service.token.TokenService;
import com.uade.recipes.service.user.UserService;
import com.uade.recipes.vo.UserVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final UserService userService;
    @Autowired
    private TokenService tokenService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    @ApiOperation(value = "Obtener un usuario en base a su alias y contraseña", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Se encontró al usuario exitosamente"),
            @ApiResponse(code = 404, message = "Usuario no encontrado"),

    })
    public ResponseEntity<UserVo> login(@RequestParam String alias, @RequestParam String password) throws UserNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByAliasAndPassword(alias, password).toVO());
    }

    @GetMapping("/token")
    @ApiOperation(value = "Genera y envía el token para continuar el proceso de recuperación de contraseña", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "El token fue generado y enviado con éxito"),
            @ApiResponse(code = 404, message = "El usuario no fue encontrado"),
            @ApiResponse(code = 422, message = "El token no pudo ser generado porque ya existe uno o bien su rol no es invitado")

    })
    public ResponseEntity<Integer> getCode(@RequestParam String email) throws UserNotFoundException, TokenCantBeGeneratedException {
        Integer userId = userService.getUserByEmail(email).getId();
        Integer token = tokenService.generateToken(userId);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @GetMapping("/check/{token}")
    @ApiOperation(value = "Controla que el token ingresado sea válido")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "El token ingresado es válido"),
            @ApiResponse(code = 406, message = "El token ingresado es invalido"),//TODO TIRAR EL ERROR
            @ApiResponse(code = 404, message = "El usuario no fue encontrado")
    })
    public ResponseEntity isValid(@PathVariable Integer token, @RequestParam String email) throws UserNotFoundException, TokenNotFoundException {
        Integer userId = userService.getUserByEmail(email).getId();
        tokenService.isTokenValid(token, userId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/password/restore")
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
