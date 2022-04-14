package com.uade.recipes.controllers;

import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;
import com.uade.recipes.exceptions.userPhotoExceptions.UserPhotoNotFoundException;
import com.uade.recipes.model.User;
import com.uade.recipes.model.UserPhoto;
import com.uade.recipes.service.userPhoto.UserPhotoService;
import com.uade.recipes.vo.UserPhotoVo;
import com.uade.recipes.vo.UserVo;
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
    public ResponseEntity<List<UserPhotoVo>> getAllUserPhotos(){
        List<UserPhotoVo> result = transformListToVoList(userPhotoService.getAllUserPhotos());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    @GetMapping("/{id}")
    @ApiOperation(value = "Get a photo from a user by his db id", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "Successfully Retrieved the user photos"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The photo was not found"),

    })
    public ResponseEntity<UserPhotoVo> getPhotoById(@PathVariable Integer id) throws UserPhotoNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(userPhotoService.getUserPhotoById(id).toVO());
    }
    @GetMapping("/recipe/{userId}")
    @ApiOperation(value = "Get a photo from a user by the user", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "Successfully Retrieved the user photo"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The user was not found"),

    })
    public ResponseEntity<UserPhotoVo> getUserPhotoByUserId(@PathVariable Integer userId) throws RecipeNotFoundException, UserNotFoundException, UserPhotoNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(userPhotoService.getUserPhotoByUserId(userId).toVO());
    }
    @PostMapping
    @ApiOperation(value = "Create a new photo user", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created the user photo"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The user was not found"),

    })
    public ResponseEntity<UserPhotoVo> savePhotoUser(@RequestParam Integer userId, @RequestParam MultipartFile image) throws RecipeNotFoundException, IOException, UserNotFoundException, UserPhotoNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(userPhotoService.saveUserPhoto(userId, image).toVO());
    }

    @DeleteMapping
    public ResponseEntity deleteUserPhoto(@RequestParam Integer photoId) throws IOException, UserPhotoNotFoundException {
        userPhotoService.deleteUserPhoto(photoId);
        return new ResponseEntity(HttpStatus.OK);
    }

    private List<UserPhotoVo> transformListToVoList(List<UserPhoto> list){
        List<UserPhotoVo> result = new ArrayList<>();
        for(UserPhoto obj: list){
            result.add(obj.toVO());
        }
        return result;
    }
}
