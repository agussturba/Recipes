package com.uade.recipes.controllers;

import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;
import com.uade.recipes.model.UserPhoto;
import com.uade.recipes.service.userPhoto.UserPhotoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/userPhotos")
public class UserPhotoController {
    private final UserPhotoService userPhotoService;

    public UserPhotoController(UserPhotoService userPhotoService) {
        this.userPhotoService = userPhotoService;
    }
    @GetMapping
    @ApiOperation(value = "Get a list of photos from a user", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Retrieved all user photos"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
    })
    public ResponseEntity<List<UserPhoto>> getAllUserPhotos(){
        return ResponseEntity.status(HttpStatus.OK).body(userPhotoService.getAllUserPhotos());
    }
    @GetMapping("/{id}")
    @ApiOperation(value = "Get a photo from a user by his db id", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "Successfully Retrieved the user photos"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The photo was not found"),

    })
    public ResponseEntity<UserPhoto> getPhotoById(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.FOUND).body(userPhotoService.getUserPhotoById(id));
    }
    @GetMapping("/recipe/{userId}")
    @ApiOperation(value = "Get a photo from a user by the user", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "Successfully Retrieved the user photo"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The user was not found"),

    })
    public ResponseEntity<UserPhoto> getUserPhotoByUserId(@PathVariable Integer userId) throws RecipeNotFoundException, UserNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(userPhotoService.getUserPhotoByUserId(userId));
    }
    @PostMapping
    @ApiOperation(value = "Create a new photo user", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created the user photo"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The user was not found"),

    })
    public ResponseEntity<UserPhoto> savePhotoUser(@RequestParam Integer id, @RequestParam Integer userId, @RequestParam MultipartFile image) throws RecipeNotFoundException, IOException {
        return ResponseEntity.status(HttpStatus.FOUND).body(userPhotoService.saveOrUpdateUserPhoto(id, userId, image));
    }
//    @PutMapping
//    public ResponseEntity<UserPhoto> updatePhoto(@RequestBody UserPhotoVo userPhotoVo, @RequestParam MultipartFile image) throws RecipeNotFoundException, IOException {
//        return ResponseEntity.status(HttpStatus.FOUND).body(userPhotoService.saveOrUpdateUserPhoto(userPhotoVo, image));
//    }
}
