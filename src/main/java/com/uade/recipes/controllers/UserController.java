package com.uade.recipes.controllers;


import com.uade.recipes.exceptions.userExceptions.*;
import com.uade.recipes.exceptions.userPhotoExceptions.UserPhotoNotFoundException;
import com.uade.recipes.model.User;
import com.uade.recipes.service.user.UserService;
import com.uade.recipes.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ApiOperation(value = "Obtener una lista de todos las usuarios", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de usuarios retornada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),

    })
    public ResponseEntity<List<UserVo>> getAllUsers() {
        List<UserVo> result = transformListToVoList(userService.getAllUsers());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/email/{email}")
    @ApiOperation(value = "Obtener un usuario en base a su email", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200 /*302*/, message = "El usuario fue encontrado"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "Usuario no encontrado"),

    })
    public ResponseEntity<UserVo> getUserByEmail(@PathVariable String email) throws UserNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByEmail(email).toVO());
    }

    @GetMapping("/username/{userName}")
    @ApiOperation(value = "Obtener un usuario en base a su alias", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200 /*302*/, message = "El usuario fue encontrado"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "Usuario no encontrado"),

    })
    public ResponseEntity<UserVo> getUserByAlias(@PathVariable String userName) throws UserNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByAlias(userName).toVO());
    }

    @PostMapping("/student")
    @ApiOperation(value = "Crear un nuevo usuario estudiante", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Usuario creado con éxito"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 409, message = "El nombre de usuario ya existe o La contraseña no se valida o el rol no es valido o el email ya existe") //TODO CHEQUEAR
    })
    public ResponseEntity<UserVo> saveUserStudent(@RequestBody UserVo userVo) throws UserNameExistsException, InvalidPasswordException, InvalidRoleException, EmailExistsException, InvalidEmailException, UserNotFoundException, UserPhotoNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveOrUpdateUser(userVo, "STUDENT").toVO());
    }
    @PostMapping("/guest")
    @ApiOperation(value = "Crear un nuevo usuario invitado", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Usuario creado con éxito"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 409, message = "El nombre de usuario ya existe o La contraseña no se valida o el rol no es valido o el email ya existe") //TODO CHEQUEAR
    })
    public ResponseEntity<UserVo> saveUserGuest(@RequestBody UserVo userVo) throws UserNameExistsException, InvalidPasswordException, InvalidRoleException, EmailExistsException, InvalidEmailException, UserNotFoundException, UserPhotoNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveOrUpdateUser(userVo, "GUEST").toVO());
    }

    @PostMapping("/admin")
    @ApiOperation(value = "Crear un nuevo usuario admin", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Usuario admin creado con éxito"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 409, message = "El nombre de usuario ya existe o La contraseña no se valida o el rol no es valido o el email ya existe") //TODO CHEQUEAR

    })
    public ResponseEntity<UserVo> saveUserAdmin(@RequestBody UserVo userVo) throws UserNameExistsException, InvalidPasswordException, InvalidRoleException, EmailExistsException, InvalidEmailException, UserNotFoundException, UserPhotoNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveOrUpdateUser(userVo, "ADMIN").toVO());
    }

    @PutMapping
    @ApiOperation(value = "Actualizar datos de un usuario", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Usuario actualizado con éxito"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 409, message = "El nombre de usuario ya existe o La contraseña no es valida o el rol no es valido o el email ya existe") //TODO CHEQUEAR

    })
    public ResponseEntity<UserVo> updateUser(@RequestBody UserVo userVo) throws UserNameExistsException, InvalidPasswordException, InvalidRoleException, EmailExistsException, InvalidEmailException, UserNotFoundException, UserPhotoNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveOrUpdateUser(userVo, userVo.getRole()).toVO());
    }

    @PostMapping("/new")
    @ApiOperation(value = "Crear un nuevo usuario en base a su alias, email y rol")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Usuario creado con éxito"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 409, message = "El nombre de usuario ya existe o el email ya existe")
    })
    public ResponseEntity<UserVo> createUser(@RequestParam String alias, @RequestParam String email, @RequestParam String role) throws UserNameExistsException, EmailExistsException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createNewUser(alias, email, role).toVO());
    }

    @PostMapping("/email/confirmation")
    @ApiOperation(value = "Confirmar email para continuación de registro")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Email confirmado con éxito"),
            @ApiResponse(code = 404, message = "El usuario no fue encontrado"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder")
    })
    public ResponseEntity confirmEmail(@RequestParam String email) throws UserNotFoundException {
        userService.confirmEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    private List<UserVo> transformListToVoList(List<User> list) {
        List<UserVo> result = new ArrayList<>();
        for (User obj : list) {
            result.add(obj.toVO());
        }
        return result;
    }
}
