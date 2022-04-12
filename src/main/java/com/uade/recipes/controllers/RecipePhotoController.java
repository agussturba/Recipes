package com.uade.recipes.controllers;

import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.model.RecipePhoto;
import com.uade.recipes.service.recipePhoto.RecipePhotoService;
import com.uade.recipes.vo.RecipePhotoVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
    @ApiOperation(value = "Get a list of Recipe Photos", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved a list Recipe Photo"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
    })
    public ResponseEntity<List<RecipePhoto>> getAllRecipePhotos(){
        return ResponseEntity.status(HttpStatus.OK).body(recipePhotoService.getAllRecipePhotos());
    }
    @GetMapping("/{id}")
    @ApiOperation(value = "Get a Recipe Photos by its db id", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "Successfully retrieved a Recipe Photo"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The Recipe photo was not found"),

    })
    public ResponseEntity<RecipePhoto> getRecipePhotoById(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.FOUND).body(recipePhotoService.getRecipePhotoById(id));
    }
    @GetMapping("/recipe/{recipeId}")
    @ApiOperation(value = "Get a list of Recipe Photos by a recipe", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved a list Recipe Photo"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The Recipe was not found"),

    })
    public ResponseEntity<List<RecipePhoto>> getRecipePhotosByRecipe(@PathVariable Integer recipeId) throws RecipeNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(recipePhotoService.getRecipePhotosByRecipeId(recipeId));
    }
    @PostMapping
    @ApiOperation(value = "Create a new Recipe Photo", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created a new Recipe Photo"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The Recipe was not found"),

    })
    public ResponseEntity<RecipePhoto> saveRecipePhoto(@RequestBody RecipePhotoVo recipePhotoVo) throws RecipeNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(recipePhotoService.saveOrUpdateRecipePhoto(recipePhotoVo));
    }
    @PutMapping
    @ApiOperation(value = "Updated a Recipe Photo", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated a Recipe Photo"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The Recipe/Recipe photo was not found"),

    })
    public ResponseEntity<RecipePhoto> updateRecipePhoto(@RequestBody RecipePhotoVo recipePhotoVo) throws RecipeNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(recipePhotoService.saveOrUpdateRecipePhoto(recipePhotoVo));
    }
}
