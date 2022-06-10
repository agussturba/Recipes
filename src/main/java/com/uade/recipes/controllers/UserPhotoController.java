package com.uade.recipes.controllers;

import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;
import com.uade.recipes.exceptions.userPhotoExceptions.UserPhotoNotFoundException;
import com.uade.recipes.model.Type;
import com.uade.recipes.model.UserPhoto;
import com.uade.recipes.service.userPhoto.UserPhotoService;
import com.uade.recipes.vo.UserPhotoVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/userPhotos")
public class UserPhotoController {
    private final UserPhotoService userPhotoService;

    public UserPhotoController(UserPhotoService userPhotoService) {
        this.userPhotoService = userPhotoService;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Obtener una foto de usuario por ID", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Foto de usuario retornada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "La foto de usuario no fue encontrada"),

    })
    public ResponseEntity<UserPhotoVo> getPhotoById(@PathVariable Integer id) throws UserPhotoNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(userPhotoService.getUserPhotoById(id).toVO());
    }
    @GetMapping("/recipe/{userId}")
    @ApiOperation(value = "Obtener una foto de usuario por usuario", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Foto de usuario retornada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "La foto de usuario no fue encontrada"),

    })
    public ResponseEntity<UserPhotoVo> getUserPhotoByUserId(@PathVariable Integer userId) throws  UserNotFoundException, UserPhotoNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(userPhotoService.getUserPhotoByUserId(userId).toVO());
    }
    @PostMapping
    @ApiOperation(value = "Crear una foto de usuario", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Foto de usuario creada exitosamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "El usuario no fue encontrado"),

    })
    public ResponseEntity<UserPhotoVo> savePhotoUser(@RequestParam Integer userId, @RequestParam MultipartFile image) throws  IOException, UserNotFoundException, UserPhotoNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(userPhotoService.saveUserPhoto(userId, image).toVO());
    }

    @DeleteMapping
    @ApiOperation(value = "Eliminar la foto de usuario", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Foto de usuario eliminada exitosamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "El usuario o foto no fue encontrado"),

    })
    public ResponseEntity deleteUserPhoto(@RequestParam Integer photoId) throws IOException, UserPhotoNotFoundException {
        userPhotoService.deleteUserPhoto(photoId);
        return new ResponseEntity(HttpStatus.OK);
    }

    private List<UserPhotoVo> transformListToVoList(List<UserPhoto> list){
        return list.stream().map(UserPhoto::toVO).collect(Collectors.toList());
    }
}
