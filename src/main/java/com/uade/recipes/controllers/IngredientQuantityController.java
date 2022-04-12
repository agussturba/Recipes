package com.uade.recipes.controllers;

import com.uade.recipes.exceptions.ingredientExceptions.IngredientNotFoundException;
import com.uade.recipes.exceptions.ingredientQuantityExceptions.IngredientQuantityNotFoundException;
import com.uade.recipes.model.IngredientQuantity;
import com.uade.recipes.service.ingredientQuantity.IngredientQuantityService;
import com.uade.recipes.vo.IngredientQuantityVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/ingredientQuantity")
public class IngredientQuantityController {
    private final IngredientQuantityService ingredientQuantityService;

    public IngredientQuantityController(IngredientQuantityService ingredientQuantityService) {
        this.ingredientQuantityService = ingredientQuantityService;
    }

    @GetMapping
    @ApiOperation(value = "Retrieve a list of ingredient quantity", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the list of ingredient quantity"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The Ingredient was not found")
    })
    public ResponseEntity<List<IngredientQuantity>> getAllIngredientQuantities(@RequestParam(required = false) Integer ingredientId) throws IngredientNotFoundException {
        if (ingredientId == null) {
            return ResponseEntity.status(HttpStatus.OK).body(ingredientQuantityService.getAllIngredientQuantity());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(ingredientQuantityService.getIngredientQuantityByIngredientId(ingredientId));
        }
    }

    @GetMapping("/{ingredientQuantityId}")
    @ApiOperation(value = "Retrieve a ingredient quantity by its db id", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "Successfully retrieved a ingredient quantity"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The ingredient quantity was not found")
    })
    public ResponseEntity<IngredientQuantity> getIngredientQuantityById(@PathVariable Integer ingredientQuantityId) throws IngredientQuantityNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(ingredientQuantityService.getIngredientQuantityById(ingredientQuantityId));
    }

    @PostMapping
    @ApiOperation(value = "Create a new ingredient quantity", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created a new ingredient quantity"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The ingredient was not found")
    })
    public ResponseEntity<IngredientQuantity> saveIngredientQuantity(@RequestBody IngredientQuantityVo ingredientQuantityVo) throws IngredientNotFoundException, IngredientQuantityNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(ingredientQuantityService.saveOrUpdateIngredientQuantity(ingredientQuantityVo));
    }

    @PutMapping
    @ApiOperation(value = "Updated an ingredient quantity", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated an ingredient quantity"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The ingredient or the Ingredient quantity id was not found")
    })
    public ResponseEntity<IngredientQuantity> updateIngredientQuantity(@RequestBody IngredientQuantityVo ingredientQuantityVo) throws IngredientNotFoundException, IngredientQuantityNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(ingredientQuantityService.saveOrUpdateIngredientQuantity(ingredientQuantityVo));
    }


}
