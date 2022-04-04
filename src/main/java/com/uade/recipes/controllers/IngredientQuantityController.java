package com.uade.recipes.controllers;

import com.uade.recipes.model.IngredientQuantity;
import com.uade.recipes.service.ingredientQuantity.IngredientQuantityService;
import com.uade.recipes.vo.IngredientQuantityVo;
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
    public ResponseEntity<List<IngredientQuantity>> getAllIngredientQuantities(@RequestParam(required = false) Integer ingredientId) {
        if (ingredientId == null) {
            return ResponseEntity.status(HttpStatus.OK).body(ingredientQuantityService.getAllIngredientQuantity());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(ingredientQuantityService.getIngredientQuantityByIngredientId(ingredientId));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredientQuantity> getIngredientQuantityById(@PathVariable Integer ingredientQuantityId) {
        return ResponseEntity.status(HttpStatus.FOUND).body(ingredientQuantityService.getIngredientQuantityById(ingredientQuantityId));
    }

    @PostMapping
    public ResponseEntity<IngredientQuantity> saveIngredientQuantity(@RequestBody IngredientQuantityVo ingredientQuantityVo) {

        return ResponseEntity.status(HttpStatus.CREATED).body(ingredientQuantityService.saveOrUpdateIngredientQuantity(ingredientQuantityVo));
    }

    @PutMapping
    public ResponseEntity<IngredientQuantity> updateIngredientQuantity(@RequestBody IngredientQuantityVo ingredientQuantityVo) {
        return ResponseEntity.status(HttpStatus.OK).body(ingredientQuantityService.saveOrUpdateIngredientQuantity(ingredientQuantityVo));
    }


}
