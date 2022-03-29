package com.uade.recipes.controllers;

import com.uade.recipes.model.Recipe;
import com.uade.recipes.service.RecipeService;
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

    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipes(@RequestParam(required = false) Integer userId, @RequestParam(required = false) Integer dishId, @RequestParam(required = false) String type) {
        if (userId == null && dishId == null && type == null) {
            return ResponseEntity.status(HttpStatus.OK).body(recipeService.getAllRecipes());
        }
        if (userId != null && dishId == null && type == null) {
            return ResponseEntity.status(HttpStatus.OK).body(recipeService.getRecipesByUserId(userId));
        }
        if (userId == null && dishId != null && type == null) {
            return ResponseEntity.status(HttpStatus.OK).body(recipeService.getRecipesByDishId(dishId));
        }
        if (userId == null && dishId == null && type != null) {
            return ResponseEntity.status(HttpStatus.OK).body(recipeService.getRecipeByType(type));
        }
        return null;//Terminar los metodos que falta
    }
    @GetMapping("/{name}")
    public ResponseEntity<Recipe> getRecipesByName(@PathVariable String name){
        return ResponseEntity.status(HttpStatus.OK).body(recipeService.getRecipeByName(name));
    }
    @GetMapping("/rating/{rating}")
    public ResponseEntity<List<Recipe>> getRecipesByRating(@PathVariable Double rating){
        return ResponseEntity.status(HttpStatus.OK).body(recipeService.getRecipeByHavingGreaterRating(rating));
    }
}
