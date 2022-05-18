package com.uade.recipes.controllers;


import com.uade.recipes.exceptions.ingredientExceptions.IngredientNameContainsNumberException;
import com.uade.recipes.exceptions.ingredientExceptions.IngredientNotFoundException;
import com.uade.recipes.exceptions.ingredientExceptions.IngredientTypeContainsNumberException;
import com.uade.recipes.model.Ingredient;
import com.uade.recipes.service.ingredient.IngredientService;
import com.uade.recipes.vo.IngredientVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @ApiOperation(value = "Obtener una lista de todos los ingredientes", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de ingredientes retornada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "Tipo de ingrediente no encontrado")
    })
    public ResponseEntity<List<IngredientVo>> getAllIngredients() {
        List<IngredientVo> result = transformListToVoList(ingredientService.getAllIngredients());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping
    @ApiOperation(value = "Crear un nuevo ingrediente", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Ingrediente creado satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "Tipo de ingrediente no encontrado")
    })
    public ResponseEntity<IngredientVo> saveIngredient(@RequestBody IngredientVo ingredientVo) throws IngredientTypeContainsNumberException, IngredientNameContainsNumberException {
        return ResponseEntity.status(HttpStatus.CREATED).body(ingredientService.saveOrUpdateIngredient(ingredientVo).toVO());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Obtener un ingrediente por su ID", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ingrediente retornado satisfactoriamente por su ID"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "Ingrediente no encontrado")
    })
    public ResponseEntity<IngredientVo> getIngredientById(@PathVariable Integer id) throws IngredientNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(ingredientService.getIngredientById(id).toVO());
    }

    @GetMapping("/name/{name}")
    @ApiOperation(value = "Obtener un ingrediente por su nombre", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ingrediente retornado satisfactoriamente por su nombre"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "Ingrediente no encontrado")
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
