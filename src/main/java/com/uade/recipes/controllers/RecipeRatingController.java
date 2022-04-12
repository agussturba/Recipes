package com.uade.recipes.controllers;

import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.exceptions.recipeRatingExceptions.RatingIsLowerThanZeroException;
import com.uade.recipes.exceptions.recipeRatingExceptions.RatingIsNullException;
import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;
import com.uade.recipes.model.RecipeRating;
import com.uade.recipes.service.recipeRating.RecipeRatingService;
import com.uade.recipes.vo.RecipeRatingVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @GetMapping("/{recipeRatingId}")
    @ApiOperation(value = "Get a Recipe Rating by his db id", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved a Recipe Rating by his db id"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The Recipe Rating was not found"),

    })
    public ResponseEntity<RecipeRating> getRecipeRatingById(@PathVariable Integer recipeRatingId) throws RecipeNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(recipeRatingService.getRecipeRatingByRecipeId(recipeRatingId));
    }

    @GetMapping("/amount/{recipeId}")
    @ApiOperation(value = "Get the amount of ratings of a recipe", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the amount of ratings of a recipe"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The Recipe was not found"),

    })
    public ResponseEntity<Integer> getAmountOfRatingsByRecipeId(@PathVariable Integer recipeId) throws RecipeNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(recipeRatingService.getAmountOfRatingsByRecipeId(recipeId));
    }

    @GetMapping("/average/{recipeId}")
    @ApiOperation(value = "Get the average of ratings of a recipe", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the average of ratings of a recipe"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The Recipe was not found"),

    })
    public ResponseEntity<Double> getAverageOfRatingByRecipeId(@PathVariable Integer recipeId) throws RecipeNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(recipeRatingService.getAverageOfRecipeRatingsByRecipeId(recipeId));
    }
    @PostMapping
    @ApiOperation(value = "Create a new rating for a recipe", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created a new rating for a recipe"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The Recipe/User was not found"),

    })
    public ResponseEntity<RecipeRating> saveRecipeRating(@RequestBody RecipeRatingVo recipeRatingVo) throws UserNotFoundException, RecipeNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeRatingService.saveOrUpdateRecipeRating(recipeRatingVo));
    }
    @PutMapping
    @ApiOperation(value = "Update a rating for a recipe", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created a new rating for a recipe"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The Recipe/rating/user was not found"),

    })
    public ResponseEntity<RecipeRating> updateRecipeRating(@RequestBody RecipeRatingVo recipeRatingVo) throws UserNotFoundException, RecipeNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeRatingService.saveOrUpdateRecipeRating(recipeRatingVo));
    }

}
