package com.uade.recipes.controllers;

import com.uade.recipes.model.RecipeRating;
import com.uade.recipes.service.RecipeRatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rating")
public class RecipeRatingController {
    private final RecipeRatingService recipeRatingService;

    public RecipeRatingController(RecipeRatingService recipeRatingService) {
        this.recipeRatingService = recipeRatingService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<RecipeRating> getRecipeRatingById(@PathVariable Integer recipeRatingId){
        return ResponseEntity.status(HttpStatus.FOUND).body(recipeRatingService.getRecipeRatingByRecipeId(recipeRatingId));
    }
    @GetMapping("/amount/{recipeId}")
    public ResponseEntity<Integer> getAmountOfRatingsByRecipeId(@PathVariable Integer recipeId){
        return ResponseEntity.status(HttpStatus.OK).body(recipeRatingService.getAmountOfRatingsByRecipeId(recipeId));
    }
    @GetMapping("/average/{recipeId}")
    public ResponseEntity<Double> getAverageOfRatingByRecipeId(@PathVariable Integer recipeId){
        return ResponseEntity.status(HttpStatus.OK).body(recipeRatingService.getAverageOfRecipeRatingsByRecipeId(recipeId));
    }
    @PutMapping
    public ResponseEntity addRatingToRecipe(@RequestParam Integer recipeId,@RequestParam Double rating){
        recipeRatingService.addRatingToRecipeByRecipeId(recipeId,rating);
        return ResponseEntity.status(HttpStatus.OK).body("Added rating with success");
    }
}
