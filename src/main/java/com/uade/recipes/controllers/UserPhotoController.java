package com.uade.recipes.controllers;

import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;
import com.uade.recipes.model.UserPhoto;
import com.uade.recipes.service.userPhoto.UserPhotoService;
import com.uade.recipes.vo.UserPhotoVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userPhotos")
public class UserPhotoController {
    private final UserPhotoService userPhotoService;

    public UserPhotoController(UserPhotoService userPhotoService) {
        this.userPhotoService = userPhotoService;
    }
    @GetMapping
    public ResponseEntity<List<UserPhoto>> getAllPhotos(){
        return ResponseEntity.status(HttpStatus.OK).body(userPhotoService.getAllUserPhotos());
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserPhoto> getPhotoById(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.FOUND).body(userPhotoService.getUserPhotoById(id));
    }
    @GetMapping("/recipe/{userId}")
    public ResponseEntity<UserPhoto> getUserPhotoByUserId(@PathVariable Integer userId) throws RecipeNotFoundException, UserNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(userPhotoService.getUserPhotoByUserId(userId));
    }
    @PostMapping
    public ResponseEntity<UserPhoto> savePhotoUser(@RequestBody UserPhotoVo userPhotoVo) throws RecipeNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(userPhotoService.saveOrUpdateUserPhoto(userPhotoVo));
    }
    @PutMapping
    public ResponseEntity<UserPhoto> updatePhoto(@RequestBody UserPhotoVo userPhotoVo) throws RecipeNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(userPhotoService.saveOrUpdateUserPhoto(userPhotoVo));
    }
}
