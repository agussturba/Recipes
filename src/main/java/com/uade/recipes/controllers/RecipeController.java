package com.uade.recipes.controllers;

import com.uade.recipes.exceptions.dishExceptions.DishNotFoundException;
import com.uade.recipes.exceptions.instructionExceptions.InstructionNotFoundException;
import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;
import com.uade.recipes.model.Recipe;
import com.uade.recipes.service.recipe.RecipeService;
import com.uade.recipes.vo.RecipeVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
    @ApiOperation(value = "Retrieve a list of recipes", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved a list of recipes"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The user/dish/people amount was not found"),

    })
    public ResponseEntity<List<Recipe>> getAllRecipes(@RequestParam(required = false) Integer userId, @RequestParam(required = false) Integer dishId, @RequestParam(required = false) Integer peopleAmount) throws DishNotFoundException, UserNotFoundException {
        if (userId == null && dishId == null && peopleAmount == null) {
            return ResponseEntity.status(HttpStatus.OK).body(recipeService.getAllRecipes());
        } else if (userId != null && dishId == null && peopleAmount == null) {
            return ResponseEntity.status(HttpStatus.OK).body(recipeService.getRecipesByUserId(userId));
        } else if (userId == null && dishId != null && peopleAmount == null) {
            return ResponseEntity.status(HttpStatus.OK).body(recipeService.getRecipesByDishId(dishId));
        } else if (userId != null && dishId != null && peopleAmount == null) {
            return ResponseEntity.status(HttpStatus.OK).body(recipeService.getRecipesByUserIdAndDishId(userId,dishId));
        } else if (userId != null && dishId == null && peopleAmount != null) {
            return ResponseEntity.status(HttpStatus.OK).body(recipeService.getRecipesByUserIdAndPeopleAmount(userId, peopleAmount));
        } else if (userId == null && dishId == null && peopleAmount != null) {
            return ResponseEntity.status(HttpStatus.OK).body(recipeService.getRecipesByPeopleAmount(peopleAmount));
        } else if (userId == null && dishId != null && peopleAmount != null) {
            return ResponseEntity.status(HttpStatus.OK).body(recipeService.getRecipesByDishIdAndPeopleAmount(dishId, peopleAmount));
        }
        return ResponseEntity.status(HttpStatus.OK).body(recipeService.getRecipesByUserIdAndDishIdAndPeopleAmount(userId, dishId, peopleAmount));
    }


    @GetMapping("/name/{name}")
    @ApiOperation(value = "Retrieve one or more recipes by its name", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved one or more recipes by its name"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The recipe was not found"),

    })
    public ResponseEntity<List<Recipe>> getRecipesByName(@PathVariable String name) {
        return ResponseEntity.status(HttpStatus.OK).body(recipeService.getRecipesByName(name));
    }

    @GetMapping("/type")
    @ApiOperation(value = "Retrieve one or more recipes by its type/s", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved one or more recipes by their type"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The recipe/s were not found"),

    })
    public ResponseEntity<List<Recipe>> getRecipesByTypes(@RequestBody List<Integer> typesIds) {//TODO  PUEDE SER UN REQUEST PARAM
        return ResponseEntity.status(HttpStatus.OK).body(recipeService.getRecipesByTypes(typesIds));
    }

    @PostMapping
    @ApiOperation(value = "Create a new recipe", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created a new recipe"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The user/dish/recipePhoto/type was not found"),

    })
    public ResponseEntity<Recipe> saveRecipe(@RequestBody RecipeVo recipeVo) throws InstructionNotFoundException, DishNotFoundException, UserNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeService.saveOrUpdateRecipe(recipeVo));
    }


    @PutMapping
    @ApiOperation(value = "Updated a recipe", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated a recipe"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The user/dish/recipePhoto/type was not found"),

    })
    public ResponseEntity<Recipe> updateRecipe(@RequestBody RecipeVo recipeVo) throws InstructionNotFoundException, DishNotFoundException, UserNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeService.saveOrUpdateRecipe(recipeVo));
    }
}
