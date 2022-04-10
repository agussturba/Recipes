package com.uade.recipes.controllers;


import com.uade.recipes.exceptions.ingredientExceptions.IngredientNameContainsNumberException;
import com.uade.recipes.exceptions.ingredientExceptions.IngredientNotFoundException;
import com.uade.recipes.exceptions.ingredientExceptions.IngredientTypeContainsNumberException;
import com.uade.recipes.model.Ingredient;
import com.uade.recipes.service.ingredient.IngredientService;
import com.uade.recipes.vo.IngredientVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredient")
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public ResponseEntity<List<Ingredient>> getAllIngredients(@RequestParam(required = false) String type) {
        if (type != null) {
            return ResponseEntity.status(HttpStatus.OK).body(ingredientService.getIngredientsByType(type));
        }
        return ResponseEntity.status(HttpStatus.OK).body(ingredientService.getAllIngredients());
    }

    @PostMapping
    public ResponseEntity<Ingredient> saveIngredient(@RequestBody IngredientVo ingredientVo) throws IngredientTypeContainsNumberException, IngredientNameContainsNumberException {
        return ResponseEntity.status(HttpStatus.CREATED).body(ingredientService.saveOrUpdateIngredient(ingredientVo));
    }

    @PutMapping
    public ResponseEntity<Ingredient> updateIngredient(@RequestBody IngredientVo ingredientVo) throws IngredientTypeContainsNumberException, IngredientNameContainsNumberException {
        return ResponseEntity.status(HttpStatus.OK).body(ingredientService.saveOrUpdateIngredient(ingredientVo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable Integer id) throws IngredientNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(ingredientService.getIngredientById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Ingredient> getIngredientByName(@PathVariable String name) throws IngredientNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(ingredientService.getIngredientByName(name));
    }

}
