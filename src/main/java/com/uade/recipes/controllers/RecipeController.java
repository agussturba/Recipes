package com.uade.recipes.controllers;

import com.uade.recipes.exceptions.dishExceptions.DishNotFoundException;
import com.uade.recipes.exceptions.ingredientExceptions.IngredientNotFoundException;
import com.uade.recipes.exceptions.instructionExceptions.InstructionNotFoundException;
import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;
import com.uade.recipes.model.IngredientQuantity;
import com.uade.recipes.model.Multimedia;
import com.uade.recipes.model.Recipe;
import com.uade.recipes.service.ingredientQuantity.IngredientQuantityService;
import com.uade.recipes.service.recipe.RecipeService;
import com.uade.recipes.vo.MultimediaVo;
import com.uade.recipes.vo.RecipeVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/recipes")
public class RecipeController {
    private final RecipeService recipeService;
    private final IngredientQuantityService ingredientQuantityService;

    public RecipeController(RecipeService recipeService, IngredientQuantityService ingredientQuantityService) {
        this.recipeService = recipeService;
        this.ingredientQuantityService = ingredientQuantityService;
    }

    @GetMapping//se hace asi por standard de rest
    @ApiOperation(value = "Retrieve a list of recipes", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved a list of recipes"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The user/dish/people amount was not found"),

    })
    public ResponseEntity<List<RecipeVo>> getAllRecipes(@RequestParam(required = false) Integer userId, @RequestParam(required = false) Integer dishId, @RequestParam(required = false) Integer peopleAmount) throws DishNotFoundException, UserNotFoundException {
        if (userId == null && dishId == null && peopleAmount == null) {
            List<RecipeVo> result  = transformListToVoList(recipeService.getAllRecipes());
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else if (userId != null && dishId == null && peopleAmount == null) {
            List<RecipeVo> result  = transformListToVoList(recipeService.getRecipesByUserId(userId));
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else if (userId == null && dishId != null && peopleAmount == null) {
            List<RecipeVo> result  = transformListToVoList(recipeService.getRecipesByDishId(dishId));
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else if (userId != null && dishId != null && peopleAmount == null) {
            List<RecipeVo> result  = transformListToVoList(recipeService.getRecipesByUserIdAndDishId(userId,dishId));
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else if (userId != null && dishId == null && peopleAmount != null) {
            List<RecipeVo> result  = transformListToVoList(recipeService.getRecipesByUserIdAndPeopleAmount(userId, peopleAmount));
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else if (userId == null && dishId == null && peopleAmount != null) {
            List<RecipeVo> result  = transformListToVoList(recipeService.getRecipesByPeopleAmount(peopleAmount));
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else if (userId == null && dishId != null && peopleAmount != null) {
            List<RecipeVo> result  = transformListToVoList(recipeService.getRecipesByDishIdAndPeopleAmount(dishId, peopleAmount));
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        List<RecipeVo> result  = transformListToVoList(recipeService.getRecipesByUserIdAndDishIdAndPeopleAmount(userId, dishId, peopleAmount));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    @GetMapping("/name/{name}")
    @ApiOperation(value = "Retrieve one or more recipes by its name", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved one or more recipes by its name"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The recipe was not found"),

    })
    public ResponseEntity<List<RecipeVo>> getRecipesByName(@PathVariable String name) {
        List<RecipeVo> result  = transformListToVoList(recipeService.getRecipesByName(name));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/type")
    @ApiOperation(value = "Retrieve one or more recipes by its type/s", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved one or more recipes by their type"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The recipe/s were not found"),

    })
    public ResponseEntity<List<RecipeVo>> getRecipesByTypes(@RequestBody List<Integer> typesIds) {//TODO  PUEDE SER UN REQUEST PARAM
        List<RecipeVo> result  = transformListToVoList(recipeService.getRecipesByTypes(typesIds));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/ingredients")
    public ResponseEntity<List<RecipeVo>> getRecipesByIngredient(@RequestParam Integer ingredientId) throws IngredientNotFoundException {
       List<Recipe> result = getRecipesFromIngredientQuantity( ingredientQuantityService.getIngredientQuantityByIngredientId(ingredientId));
        List<RecipeVo> resultFinal  = transformListToVoList(result);
       return ResponseEntity.status(HttpStatus.FOUND).body(resultFinal);
    }

    @PostMapping
    @ApiOperation(value = "Create a new recipe", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created a new recipe"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The user/dish/recipePhoto/type was not found"),

    })
    public ResponseEntity<RecipeVo> saveRecipe(@RequestBody RecipeVo recipeVo) throws InstructionNotFoundException, DishNotFoundException, UserNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeService.saveOrUpdateRecipe(recipeVo).toVO());
    }


    @PutMapping
    @ApiOperation(value = "Updated a recipe", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated a recipe"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The user/dish/recipePhoto/type was not found"),

    })
    public ResponseEntity<RecipeVo> updateRecipe(@RequestBody RecipeVo recipeVo) throws InstructionNotFoundException, DishNotFoundException, UserNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeService.saveOrUpdateRecipe(recipeVo).toVO());
    }

    private List<Recipe> getRecipesFromIngredientQuantity (List<IngredientQuantity> list){
        List<Recipe> resultList = new ArrayList<>();
        for (IngredientQuantity ingredient : list){
            resultList.add(ingredient.getRecipe());
        }
        return resultList;
    }

    private List<RecipeVo> transformListToVoList(List<Recipe> list){
        List<RecipeVo> result = new ArrayList<>();
        for(Recipe obj: list){
            result.add(obj.toVO());
        }
        return result;
    }
}
