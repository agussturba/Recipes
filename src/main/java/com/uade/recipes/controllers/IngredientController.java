package com.uade.recipes.controllers;


import com.uade.recipes.model.Dish;
import com.uade.recipes.model.Ingredient;
import com.uade.recipes.service.SequenceGeneratorService;
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
    private final SequenceGeneratorService sequenceGeneratorService;

    public IngredientController(IngredientService ingredientService, SequenceGeneratorService sequenceGeneratorService) {
        this.ingredientService = ingredientService;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @GetMapping
    public ResponseEntity<List<Ingredient>> getAllIngredients(@RequestParam(required = false) String type) {
        if (type != null) {
            return ResponseEntity.status(HttpStatus.OK).body(ingredientService.getIngredientsByType(type));
        }
        return ResponseEntity.status(HttpStatus.OK).body(ingredientService.getAllIngredients());
    }

    @PostMapping
    public ResponseEntity<Ingredient> saveIngredient(@RequestBody IngredientVo ingredientVo) {
        ingredientVo.setId(sequenceGeneratorService.generateSequence(Ingredient.SEQUENCE_NAME));
        return ResponseEntity.status(HttpStatus.CREATED).body(ingredientService.saveOrUpdateIngredient(ingredientVo));
    }

    @PutMapping
    public ResponseEntity<Ingredient> updateIngredient(@RequestBody IngredientVo ingredientVo) {
        return ResponseEntity.status(HttpStatus.OK).body(ingredientService.saveOrUpdateIngredient(ingredientVo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(ingredientService.getIngredientById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Ingredient> getIngredientByName(@PathVariable String name) {
        return ResponseEntity.status(HttpStatus.OK).body(ingredientService.getIngredientByName(name));
    }

}
