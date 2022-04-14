package com.uade.recipes.controllers;


import com.uade.recipes.exceptions.ingredientExceptions.IngredientNameContainsNumberException;
import com.uade.recipes.exceptions.ingredientExceptions.IngredientNotFoundException;
import com.uade.recipes.exceptions.ingredientExceptions.IngredientTypeContainsNumberException;
import com.uade.recipes.model.Dish;
import com.uade.recipes.model.Ingredient;
import com.uade.recipes.service.ingredient.IngredientService;
import com.uade.recipes.vo.DishVo;
import com.uade.recipes.vo.IngredientVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/ingredient")
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    @ApiOperation(value = "Get a list of ingredients", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved a list of ingredients"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The type was not found")
    })
    public ResponseEntity<List<IngredientVo>> getAllIngredients(@RequestParam(required = false) String type) {
        if (type != null) {
            List<IngredientVo> result = transformListToVoList(ingredientService.getIngredientsByType(type));
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        List<IngredientVo> result = transformListToVoList(ingredientService.getAllIngredients());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping
    @ApiOperation(value = "Creat a new ingredient", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created a new ingredient"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The type was not found")
    })
    public ResponseEntity<IngredientVo> saveIngredient(@RequestBody IngredientVo ingredientVo) throws IngredientTypeContainsNumberException, IngredientNameContainsNumberException {
        return ResponseEntity.status(HttpStatus.CREATED).body(ingredientService.saveOrUpdateIngredient(ingredientVo).toVO());
    }

    @PutMapping
    @ApiOperation(value = "Update an ingredient", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully updated an ingredient"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The type was not found")
    })
    public ResponseEntity<IngredientVo> updateIngredient(@RequestBody IngredientVo ingredientVo) throws IngredientTypeContainsNumberException, IngredientNameContainsNumberException {
        return ResponseEntity.status(HttpStatus.OK).body(ingredientService.saveOrUpdateIngredient(ingredientVo).toVO());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get an ingredient by its db Id", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 302 , message = "Successfully retrieved an ingredient by its id"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The ingredient was not found")
    })
    public ResponseEntity<IngredientVo> getIngredientById(@PathVariable Integer id) throws IngredientNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(ingredientService.getIngredientById(id).toVO());
    }

    @GetMapping("/name/{name}")
    @ApiOperation(value = "Get an ingredient by its name", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 302 , message = "Successfully retrieved an ingredient by its name"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The ingredient was not found")
    })
    public ResponseEntity<IngredientVo> getIngredientByName(@PathVariable String name) throws IngredientNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(ingredientService.getIngredientByName(name).toVO());
    }

    private List<IngredientVo> transformListToVoList(List<Ingredient> list){
        List<IngredientVo> result = new ArrayList<>();
        for(Ingredient ing : list){
            result.add(ing.toVO());
        }
        return result;
    }

}
