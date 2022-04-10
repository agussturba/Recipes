package com.uade.recipes.controllers;

import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.exceptions.recipeRatingExceptions.RatingIsLowerThanZeroException;
import com.uade.recipes.exceptions.recipeRatingExceptions.RatingIsNullException;
import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;
import com.uade.recipes.model.RecipeRating;
import com.uade.recipes.service.recipeRating.RecipeRatingService;
import com.uade.recipes.vo.RecipeRatingVo;
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
    public ResponseEntity<RecipeRating> getRecipeRatingById(@PathVariable Integer recipeRatingId) throws RecipeNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(recipeRatingService.getRecipeRatingByRecipeId(recipeRatingId));
    }

    @GetMapping("/amount/{recipeId}")
    public ResponseEntity<Integer> getAmountOfRatingsByRecipeId(@PathVariable Integer recipeId) throws RecipeNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(recipeRatingService.getAmountOfRatingsByRecipeId(recipeId));
    }

    @GetMapping("/average/{recipeId}")
    public ResponseEntity<Double> getAverageOfRatingByRecipeId(@PathVariable Integer recipeId) throws RecipeNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(recipeRatingService.getAverageOfRecipeRatingsByRecipeId(recipeId));
    }
    @PostMapping
    public ResponseEntity<RecipeRating> saveRecipeRating(@RequestBody RecipeRatingVo recipeRatingVo) throws UserNotFoundException, RecipeNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeRatingService.saveOrUpdateRecipeRating(recipeRatingVo));
    }
    @PutMapping
    public ResponseEntity<RecipeRating> updateRecipeRating(@RequestBody RecipeRatingVo recipeRatingVo) throws UserNotFoundException, RecipeNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeRatingService.saveOrUpdateRecipeRating(recipeRatingVo));
    }

}
