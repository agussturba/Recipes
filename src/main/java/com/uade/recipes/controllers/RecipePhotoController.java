package com.uade.recipes.controllers;

import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.model.Recipe;
import com.uade.recipes.model.RecipePhoto;
import com.uade.recipes.service.recipePhoto.RecipePhotoService;
import com.uade.recipes.vo.RecipePhotoVo;
import com.uade.recipes.vo.RecipeVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.util.ArrayList;
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
    public ResponseEntity<List<RecipePhotoVo>> getAllRecipePhotos() {
        List<RecipePhotoVo> result = transformListToVoList(recipePhotoService.getAllRecipePhotos());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a Recipe Photos by its db id", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "Successfully retrieved a Recipe Photo"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The Recipe photo was not found"),

    })
    public ResponseEntity<RecipePhotoVo> getRecipePhotoById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.FOUND).body(recipePhotoService.getRecipePhotoById(id).toVO());
    }

    @GetMapping("/recipe/{recipeId}")
    @ApiOperation(value = "Get a list of Recipe Photos by a recipe", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved a list Recipe Photo"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The Recipe was not found"),

    })
    public ResponseEntity<List<RecipePhotoVo>> getRecipePhotosByRecipe(@PathVariable Integer recipeId) throws RecipeNotFoundException {
        List<RecipePhotoVo> result = transformListToVoList(recipePhotoService.getRecipePhotosByRecipeId(recipeId));
        return ResponseEntity.status(HttpStatus.FOUND).body(result);
    }

    @PostMapping
    @ApiOperation(value = "Create a new Recipe Photo", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created a new Recipe Photo"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The Recipe was not found"),

    })
    public ResponseEntity<List<RecipePhotoVo>> saveRecipePhoto(@RequestParam Integer recipeId, @RequestParam List<MultipartFile> images) throws RecipeNotFoundException, IOException {
        List<RecipePhotoVo> result = transformListToVoList( (List<RecipePhoto>) recipePhotoService.saveRecipePhoto(recipeId, images));
        return ResponseEntity.status(HttpStatus.FOUND).body(result);
    }

    @DeleteMapping
    public ResponseEntity deleteRecipePhoto(@RequestParam Integer recipeId, @RequestParam Integer recipePhotoId) throws RecipeNotFoundException, IOException {
        recipePhotoService.deleteRecipePhoto(recipeId, recipePhotoId);
        return new ResponseEntity(HttpStatus.FOUND);
    }

    private List<RecipePhotoVo> transformListToVoList(List<RecipePhoto> list){
        List<RecipePhotoVo> result = new ArrayList<>();
        for(RecipePhoto obj: list){
            result.add(obj.toVO());
        }
        return result;
    }
//    @PutMapping
//    @ApiOperation(value = "Updated a Recipe Photo", response = ResponseEntity.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successfully updated a Recipe Photo"),
//            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
//            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
//            @ApiResponse(code = 404, message = "The Recipe/Recipe photo was not found"),
//
//    })
//    public ResponseEntity<List<RecipePhoto>> updateRecipePhoto(@RequestParam Integer recipeId, @RequestParam List<Integer> recipePhotosIds, @RequestParam List<MultipartFile> newImages) throws RecipeNotFoundException {
//        return ResponseEntity.status(HttpStatus.FOUND).body((List<RecipePhoto>) recipePhotoService.updateRecipePhoto(recipeId, recipePhotosIds, newImages));
//    }
}
