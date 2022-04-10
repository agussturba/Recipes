package com.uade.recipes.controllers;

import com.uade.recipes.exceptions.dishExceptions.DishNotFoundException;
import com.uade.recipes.exceptions.instructionExceptions.InstructionNotFoundException;
import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;
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
    public ResponseEntity<List<Recipe>> getAllRecipes(@RequestParam(required = false) Integer userId, @RequestParam(required = false) Integer dishId) throws DishNotFoundException, UserNotFoundException {
        if (userId == null && dishId == null) {
            return ResponseEntity.status(HttpStatus.OK).body(recipeService.getAllRecipes());
        } else if (userId != null && dishId == null) {
            return ResponseEntity.status(HttpStatus.OK).body(recipeService.getRecipesByUserId(userId));
        } else if (userId == null && dishId != null) {
            return ResponseEntity.status(HttpStatus.OK).body(recipeService.getRecipesByDishId(dishId));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(recipeService.getRecipesByUserIdAndDishId(userId, dishId));
        }
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<Recipe>> getRecipesByName(@PathVariable String name) {
        return ResponseEntity.status(HttpStatus.OK).body(recipeService.getRecipeByName(name));
    }

    @PostMapping
    public ResponseEntity<Recipe> saveRecipe(@RequestBody RecipeVo recipeVo) throws InstructionNotFoundException, DishNotFoundException, UserNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeService.saveOrUpdateRecipe(recipeVo));
    }

    @PutMapping
    public ResponseEntity<Recipe> updateRecipe(@RequestBody RecipeVo recipeVo) throws InstructionNotFoundException, DishNotFoundException, UserNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeService.saveOrUpdateRecipe(recipeVo));
    }
}
