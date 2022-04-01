package com.uade.recipes.controllers;

import com.uade.recipes.model.Recipe;
import com.uade.recipes.service.recipe.RecipeService;
import com.uade.recipes.vo.RecipeVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/recipes")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping//se hace asi por standard de rest
    public ResponseEntity<List<Recipe>> getAllRecipes(@RequestParam(required = false) Integer userId, @RequestParam(required = false) Integer dishId, @RequestParam(required = false) String type) {
        if (userId == null && dishId == null && type == null) {
            return ResponseEntity.status(HttpStatus.OK).body(recipeService.getAllRecipes());
        } else if (userId != null && dishId == null && type == null) {
            return ResponseEntity.status(HttpStatus.OK).body(recipeService.getRecipesByUserId(userId));
        } else if (userId == null && dishId != null && type == null) {
            return ResponseEntity.status(HttpStatus.OK).body(recipeService.getRecipesByDishId(dishId));
        } else if (userId == null && dishId == null && type != null) {
            return ResponseEntity.status(HttpStatus.OK).body(recipeService.getRecipeByType(type));
        } else if (userId != null && dishId != null && type == null) {
            return ResponseEntity.status(HttpStatus.OK).body(recipeService.getRecipesByUserIdAndDishId(userId, dishId));
        } else if (userId != null && dishId == null && type != null) {
            return ResponseEntity.status(HttpStatus.OK).body(recipeService.getRecipesByUserIdAndType(userId, type));
        } else if (userId == null && dishId != null && type != null) {
            return ResponseEntity.status(HttpStatus.OK).body(recipeService.getRecipesByDishIdAndType(dishId, type));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(recipeService.getRecipesByDishIdAndTypeAndUserId(dishId, type, userId));
        }

    }

    @GetMapping("/{name}")
    public ResponseEntity<Recipe> getRecipesByName(@PathVariable String name) {
        return ResponseEntity.status(HttpStatus.OK).body(recipeService.getRecipeByName(name));
    }
    @PostMapping
    public ResponseEntity<Recipe> saveRecipe(@RequestBody RecipeVo recipeVo){
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeService.saveOrUpdateRecipe(recipeVo));
    }
    @PutMapping
    public ResponseEntity<Recipe> updateRecipe(@RequestBody RecipeVo recipeVo){
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeService.saveOrUpdateRecipe(recipeVo));
    }
}
