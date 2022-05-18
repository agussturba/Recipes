package com.uade.recipes.controllers;

import com.uade.recipes.exceptions.ingredientExceptions.CannotDivideTheIngredientException;
import com.uade.recipes.exceptions.ingredientExceptions.IngredientNotFoundException;
import com.uade.recipes.exceptions.ingredientQuantityExceptions.IngredientQuantityNotFoundException;
import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.model.IngredientQuantity;
import com.uade.recipes.service.ingredientQuantity.IngredientQuantityService;
import com.uade.recipes.vo.IngredientQuantityVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @ApiOperation(value = "Obtener una lista de todas las cantidades de ingredientes una receta", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de cantidad de ingrediente devuelta satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "Receta no encontrada")
    })
    public ResponseEntity<List<IngredientQuantityVo>> getAllIngredientQuantitiesByRecipeId(@RequestParam Integer recipeId) throws RecipeNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(transformListToVoList(ingredientQuantityService.getIngredientQuantityByRecipeId(recipeId)));
    }

    @GetMapping("/{ingredientQuantityId}")
    @ApiOperation(value = "Obtener una cantidades de ingrediente por ID", response = Iterable.class)
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
    @ApiOperation(value = "Convertir la unidad de la relacion ingrediente-cantidad a partir de una nueva unidad ", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Conversion realizada"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "Conversion o Unidad o la relacion ingrediente-cantidad no encontrada")
    })
    public ResponseEntity<IngredientQuantityVo> convertIngredientQuantityUnitByTargetUnitId(@RequestBody IngredientQuantityVo ingredientQuantityVo, @RequestParam Integer targetUnitId) {
        return ResponseEntity.status(HttpStatus.OK).body(ingredientQuantityService.convertIngredientQuantityUnitByTargetUnitId(ingredientQuantityVo,targetUnitId));
    }

    @PostMapping
    @ApiOperation(value = "Crear una nueva cantidad de ingrediente para una receta", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Cantidad de ingrediente creada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "Ingrediente o Receta o Unidad no encontrada")
    })
    public ResponseEntity<IngredientQuantityVo> saveIngredientQuantity(@RequestBody IngredientQuantityVo ingredientQuantityVo) throws IngredientNotFoundException, IngredientQuantityNotFoundException, RecipeNotFoundException, CannotDivideTheIngredientException {
        return ResponseEntity.status(HttpStatus.CREATED).body(ingredientQuantityService.saveOrUpdateIngredientQuantity(ingredientQuantityVo).toVO());
    }

    @PutMapping
    @ApiOperation(value = "Actualizar una cantidad de ingrediente", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cantidad de ingrediente actualizada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "Ingrediente o Receta o Unidad no encontrada")
    })
    public ResponseEntity<IngredientQuantityVo> updateIngredientQuantity(@RequestBody IngredientQuantityVo ingredientQuantityVo) throws IngredientNotFoundException, IngredientQuantityNotFoundException, RecipeNotFoundException, CannotDivideTheIngredientException {
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
