package com.uade.recipes.controllers;

import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.model.RecipePhoto;
import com.uade.recipes.service.recipePhoto.RecipePhotoService;
import com.uade.recipes.vo.RecipePhotoVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipePhotos")
public class RecipePhotoController {
    private final RecipePhotoService recipePhotoService;

    public RecipePhotoController(RecipePhotoService recipePhotoService) {
        this.recipePhotoService = recipePhotoService;
    }
    @GetMapping
    public ResponseEntity<List<RecipePhoto>> getAllPhotos(){
        return ResponseEntity.status(HttpStatus.OK).body(recipePhotoService.getAllRecipePhotos());
    }
    @GetMapping("/{id}")
    public ResponseEntity<RecipePhoto> getPhotoById(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.FOUND).body(recipePhotoService.getRecipePhotoById(id));
    }
    @GetMapping("/recipe/{recipeId}")
    public ResponseEntity<List<RecipePhoto>> getPhotosByRecipe(@PathVariable Integer recipeId) throws RecipeNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(recipePhotoService.getRecipePhotosByRecipeId(recipeId));
    }
    @PostMapping
    public ResponseEntity<RecipePhoto> savePhoto(@RequestBody RecipePhotoVo recipePhotoVo) throws RecipeNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(recipePhotoService.saveOrUpdateRecipePhoto(recipePhotoVo));
    }
    @PutMapping
    public ResponseEntity<RecipePhoto> updatePhoto(@RequestBody RecipePhotoVo recipePhotoVo) throws RecipeNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(recipePhotoService.saveOrUpdateRecipePhoto(recipePhotoVo));
    }
}
