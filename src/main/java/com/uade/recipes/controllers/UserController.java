package com.uade.recipes.controllers;


import com.uade.recipes.exceptions.userExceptions.*;
import com.uade.recipes.model.User;
import com.uade.recipes.service.user.UserService;
import com.uade.recipes.vo.UserVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
//test
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Obtener un usuario en base a su id", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200 /*302*/, message = "El usuario fue encontrado"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "Usuario no encontrado"),

    })
    public ResponseEntity<UserVo> getUserById(@PathVariable Integer id) throws UserNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(id).toVO());
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

    @GetMapping("/alias/{currentAlias}")
    @ApiOperation(value = "Obtener una sugerencia para el alias de un usuario", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200 /*302*/, message = "se a retornado un alias sugerido "),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "Usuario no encontrado"),

    })
    public ResponseEntity<String> getSuggestedAlias(@PathVariable String currentAlias) {
        return ResponseEntity.status(HttpStatus.OK).body("Alias");
    }

    @PostMapping("/student")
    @ApiOperation(value = "Crear un nuevo usuario estudiante", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Usuario estudiante creado con éxito"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 409, message = "El nombre de usuario ya existe o La contraseña no se valida o el rol no es valido o el email ya existe") //TODO CHEQUEAR
    })
    public ResponseEntity<UserVo> saveUserStudent(@RequestBody UserVo userVo) throws UserNameExistsException, InvalidPasswordException, InvalidRoleException, EmailExistsException, InvalidEmailException, UserNotFoundException {
        userVo.setRole("STUDENT");
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(userVo).toVO());
    }

    @PostMapping("/guest")
    @ApiOperation(value = "Crear un nuevo usuario invitado", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Usuario invitado creado con éxito"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 409, message = "El nombre de usuario ya existe o La contraseña no se valida o el rol no es valido o el email ya existe") //TODO CHEQUEAR
    })
    public ResponseEntity<UserVo> saveUserGuest(@RequestBody UserVo userVo) throws UserNameExistsException, EmailExistsException {
        userVo.setRole("GUEST");
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(userVo).toVO());
    }

    @PostMapping("/save")
    @ApiOperation(value = "Salvar una lista de usuarios", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Usuarios creados"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 409, message = "El nombre de usuario ya existe o La contraseña no se valida o el rol no es valido o el email ya existe") //TODO CHEQUEAR
    })
    public void saveUserGuest(@RequestBody List<UserVo> userVoList) {
        List<User> users = userVoList.stream().map(UserVo::toModel).collect(Collectors.toList());
        userService.saveAllUsers(users);
    }


    @PutMapping
    @ApiOperation(value = "Actualizar datos de un usuario", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Usuario actualizado con éxito"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 409, message = "El nombre de usuario ya existe o La contraseña no es valida o el rol no es valido o el email ya existe") //TODO CHEQUEAR

    })
    public ResponseEntity<UserVo> updateUser(@RequestBody UserVo userVo) throws UserNameExistsException, EmailExistsException, UserNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.updateUser(userVo).toVO());
    }

    @GetMapping("/email/confirmation")
    @ApiOperation(value = "Confirmar email para continuación de registro")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Email confirmado con éxito"),
            @ApiResponse(code = 404, message = "El usuario no fue encontrado"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder")
    })
    public ResponseEntity confirmEmail(@RequestParam String email) throws UserNotFoundException {
        userService.confirmEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body("Gracias por verificar tu correo! Ya puedes regresar a la app");
    }

    @GetMapping("/check/registration/completion")
    @ApiOperation(value = "Chequear si un email pertenece a un registro completo")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "El email corresponde a un registro completo"),
            @ApiResponse(code = 404, message = "El usuario no fue encontrado"),
            @ApiResponse(code = 409, message = "El email corresponde a un registro incompleto"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder")
    })
    public ResponseEntity isRegistrationComplete(@RequestParam String email) throws UserNotFoundException, RegistrationProcessIncompleteException {
        userService.isRegistryComplete(email);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @PostMapping("/photo")
    @ApiOperation(value = "Crear una foto de usuario", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Foto de usuario creada exitosamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "El usuario no fue encontrado"),

    })
    public ResponseEntity<String> savePhotoUser(@RequestParam Integer userId, @RequestParam MultipartFile image) throws IOException, UserNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.saveUserPhoto(userId, image));
    }

    @DeleteMapping("/photo")
    @ApiOperation(value = "Eliminar la foto de usuario", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Foto de usuario eliminada exitosamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "El usuario o foto no fue encontrado"),

    })
    public ResponseEntity deleteUserPhoto(@RequestParam Integer userId) throws IOException, UserNotFoundException {
        userService.deleteUserPhoto(userId);
        return new ResponseEntity(HttpStatus.OK);
    }

    private List<UserVo> transformListToVoList(List<User> list) {
        return list.stream().map(User::toVO).collect(Collectors.toList());
    }
}
