package com.uade.recipes.controllers;

import com.uade.recipes.exceptions.ingredientExceptions.IngredientNotFoundException;
import com.uade.recipes.exceptions.ingredientQuantityExceptions.IngredientQuantityNotFoundException;
import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.model.Ingredient;
import com.uade.recipes.model.IngredientQuantity;
import com.uade.recipes.service.ingredientQuantity.IngredientQuantityService;
import com.uade.recipes.vo.IngredientQuantityVo;
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
@RequestMapping("api/ingredientQuantity")
public class IngredientQuantityController {
    private final IngredientQuantityService ingredientQuantityService;

    public IngredientQuantityController(IngredientQuantityService ingredientQuantityService) {
        this.ingredientQuantityService = ingredientQuantityService;
    }

    @GetMapping
    @ApiOperation(value = "Recuperar una lista de cantidad de ingrediente", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de cantidad de ingrediente devuelta satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "Ingrediente no encontrado")
    })
    public ResponseEntity<List<IngredientQuantityVo>> getAllIngredientQuantities(@RequestParam(required = false) Integer ingredientId) throws IngredientNotFoundException {
        if (ingredientId == null) {
            List<IngredientQuantityVo> result = transformListToVoList(ingredientQuantityService.getAllIngredientQuantity());
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else {
            List<IngredientQuantityVo> result = transformListToVoList(ingredientQuantityService.getIngredientQuantityByIngredientId(ingredientId));
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }

    @GetMapping("/{ingredientQuantityId}")
    @ApiOperation(value = "Obtener una cantidad de ingrediente por ID", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cantidad de ingrediente devuelta satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "Ingrediente no encontrado")
    })
    public ResponseEntity<IngredientQuantityVo> getIngredientQuantityById(@PathVariable Integer ingredientQuantityId) throws IngredientQuantityNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(ingredientQuantityService.getIngredientQuantityById(ingredientQuantityId).toVO());
    }
    @GetMapping("/convert")
    @ApiOperation(value = "Convertir la relacion ingrediente cantidad dependiendo de la unidad que se le pase ", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Conversion realizada"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "Conversion no encontrada")
    })
    public ResponseEntity<IngredientQuantityVo> convertIngredientQuantityUnitByTargetUnitId(@RequestBody IngredientQuantityVo ingredientQuantityVo, @RequestParam Integer targetUnitId) {
        return ResponseEntity.status(HttpStatus.OK).body(ingredientQuantityService.convertIngredientQuantityUnitByTargetUnitId(ingredientQuantityVo,targetUnitId));
    }

    @PostMapping
    @ApiOperation(value = "Crear una nueva cantidad de ingrediente", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cantidad de ingrediente creada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "Ingrediente no encontrado")
    })
    public ResponseEntity<IngredientQuantityVo> saveIngredientQuantity(@RequestBody IngredientQuantityVo ingredientQuantityVo) throws IngredientNotFoundException, IngredientQuantityNotFoundException, RecipeNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(ingredientQuantityService.saveOrUpdateIngredientQuantity(ingredientQuantityVo).toVO());
    }

    @PutMapping
    @ApiOperation(value = "Actualizar una cantidad de ingrediente", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cantidad de ingrediente actualizada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "Ingrediente no encontrado")
    })
    public ResponseEntity<IngredientQuantityVo> updateIngredientQuantity(@RequestBody IngredientQuantityVo ingredientQuantityVo) throws IngredientNotFoundException, IngredientQuantityNotFoundException, RecipeNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(ingredientQuantityService.saveOrUpdateIngredientQuantity(ingredientQuantityVo).toVO());
    }

    private List<IngredientQuantityVo> transformListToVoList(List<IngredientQuantity> list){
        List<IngredientQuantityVo> result = new ArrayList<>();
        for(IngredientQuantity ing : list){
            result.add(ing.toVO());
        }
        return result;
    }

}
