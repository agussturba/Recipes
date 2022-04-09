package com.uade.recipes.controllers;

import com.uade.recipes.model.Photo;
import com.uade.recipes.service.photo.PhotoService;
import com.uade.recipes.vo.PhotoVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {
    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }
    @GetMapping
    public ResponseEntity<List<Photo>> getAllPhotos(){
        return ResponseEntity.status(HttpStatus.OK).body(photoService.getAllPhotos());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Photo> getPhotoById(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.FOUND).body(photoService.getPhotoById(id));
    }
    @GetMapping("/recipe/{recipeId}")
    public ResponseEntity<List<Photo>> getPhotosByRecipe(@PathVariable Integer recipeId){
        return ResponseEntity.status(HttpStatus.FOUND).body(photoService.getPhotosByRecipeId(recipeId));
    }
    @PostMapping
    public ResponseEntity<Photo> savePhoto(@RequestBody PhotoVo photoVo){
        return ResponseEntity.status(HttpStatus.FOUND).body(photoService.saveOrUpdatePhoto(photoVo));
    }
    @PutMapping
    public ResponseEntity<Photo> updatePhoto(@RequestBody PhotoVo photoVo){
        return ResponseEntity.status(HttpStatus.FOUND).body(photoService.saveOrUpdatePhoto(photoVo));
    }
}
