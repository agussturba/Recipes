package com.uade.recipes.controllers;


import com.uade.recipes.exceptions.userExceptions.*;
import com.uade.recipes.exceptions.userPhotoExceptions.UserPhotoNotFoundException;
import com.uade.recipes.model.User;
import com.uade.recipes.service.user.UserService;
import com.uade.recipes.vo.UserVo;
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
    @ApiOperation(value = "Obtener una lista de todos los usuarios", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Se obtuvo exitosamente la lista de usuarios"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),

    })
    public ResponseEntity<List<UserVo>> getAllUsers() {
        List<UserVo> result = transformListToVoList(userService.getAllUsers());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/email/{email}")
    @ApiOperation(value = "Obtener un usuario en base a su email", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200 /*302*/, message = "El usuario fue encontrado"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "Usuario no encontrado"),

    })
    public ResponseEntity<UserVo> getUserByEmail(@PathVariable String email) throws UserNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByEmail(email).toVO());
    }

    @GetMapping("/username/{userName}")
    @ApiOperation(value = "Obtener un usuario en base a su alias", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200 /*302*/, message = "El usuario fue encontrado"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "Usuario no encontrado"),

    })
    public ResponseEntity<UserVo> getUserByAlias(@PathVariable String userName) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByAlias(userName).toVO());
    }

    @PostMapping
    @ApiOperation(value = "Crear un nuevo usuario", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Usuario creado con éxito"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 409, message = "El nombre de usuario ya existe o La contraseña no se valida o el rol no es valido o el email ya existe") //TODO CHEQUEAR
    })
    public ResponseEntity<UserVo> saveUser(@RequestBody UserVo userVo) throws UserNameExistsException, InvalidPasswordException, InvalidRoleException, EmailExistsException, InvalidEmailException, UserNotFoundException, UserPhotoNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveOrUpdateUser(userVo, userVo.getRole()).toVO());
    }

//    @PostMapping("/admin")
//    @ApiOperation(value = "Crear un nuevo usuario admin", response = ResponseEntity.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 201, message = "Usuario admin creado con éxito"),
//            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
//            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
//            @ApiResponse(code = 409, message = "El nombre de usuario ya existe o La contraseña no se valida o el rol no es valido o el email ya existe") //TODO CHEQUEAR
//
//    })
//    public ResponseEntity<UserVo> saveUserStudent(@RequestBody UserVo userVo) throws UserNameExistsException, InvalidPasswordException, InvalidRoleException, EmailExistsException, InvalidEmailException, UserNotFoundException, UserPhotoNotFoundException {
//        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveOrUpdateUser(userVo, "ROLE_ADMIN").toVO());
//    }

    @PutMapping
    @ApiOperation(value = "Actualizar datos de un usuario", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Usuario actualizado con éxito"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 409, message = "El nombre de usuario ya existe o La contraseña no se valida o el rol no es valido o el email ya existe") //TODO CHEQUEAR

    })
    public ResponseEntity<UserVo> updateUser(@RequestBody UserVo userVo) throws UserNameExistsException, InvalidPasswordException, InvalidRoleException, EmailExistsException, InvalidEmailException, UserNotFoundException, UserPhotoNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveOrUpdateUser(userVo, userVo.getRole()).toVO());
    }

    private List<UserVo> transformListToVoList(List<User> list) {
        List<UserVo> result = new ArrayList<>();
        for (User obj : list) {
            result.add(obj.toVO());
        }
        return result;
    }
}
