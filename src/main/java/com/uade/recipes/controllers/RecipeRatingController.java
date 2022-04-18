package com.uade.recipes.controllers;

import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.exceptions.recipeRatingExceptions.RatingIsLowerThanZeroException;
import com.uade.recipes.exceptions.recipeRatingExceptions.RatingIsNullException;
import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;
import com.uade.recipes.model.RecipePhoto;
import com.uade.recipes.model.RecipeRating;
import com.uade.recipes.service.recipeRating.RecipeRatingService;
import com.uade.recipes.vo.RecipePhotoVo;
import com.uade.recipes.vo.RecipeRatingVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/rating")
public class RecipeRatingController {
    private final RecipeRatingService recipeRatingService;

    public RecipeRatingController(RecipeRatingService recipeRatingService) {
        this.recipeRatingService = recipeRatingService;
    }

    @GetMapping("/{recipeRatingId}")
    @ApiOperation(value = "Obtener una calificacion de receta por ID", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Calificacion de receta retornada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "La calificacion de receta no fue encontrada"),

    })
    public ResponseEntity<RecipeRatingVo> getRecipeRatingById(@PathVariable Integer recipeRatingId) throws RecipeNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(recipeRatingService.getRecipeRatingByRecipeId(recipeRatingId).toVO());
    }

    @GetMapping("/amount/{recipeId}")
    @ApiOperation(value = "Obtener la cantidad de calificaciones de una receta", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cantidad de calificaciones de la receta retornada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "La receta no fue encontrada"),

    })
    public ResponseEntity<Integer> getAmountOfRatingsByRecipeId(@PathVariable Integer recipeId) throws RecipeNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(recipeRatingService.getAmountOfRatingsByRecipeId(recipeId));
    }

    @GetMapping("/average/{recipeId}")
    @ApiOperation(value = "Obtener el promedio de calificaciones de una receta", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Promedio de calificaciones de la receta retornada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "La receta no fue encontrada"),

    })
    public ResponseEntity<Double> getAverageOfRatingByRecipeId(@PathVariable Integer recipeId) throws RecipeNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(recipeRatingService.getAverageOfRecipeRatingsByRecipeId(recipeId));
    }
    @PostMapping
    @ApiOperation(value = "Crear una nueva calificacion de receta ", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Calificacion de receta creada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "La receta/usuario no fue encontrado"),

    })
    public ResponseEntity<RecipeRatingVo> saveRecipeRating(@RequestBody RecipeRatingVo recipeRatingVo) throws UserNotFoundException, RecipeNotFoundException, RatingIsLowerThanZeroException, RatingIsNullException {
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeRatingService.saveOrUpdateRecipeRating(recipeRatingVo).toVO());
    }
    @PutMapping
    @ApiOperation(value = "Actualizar una calificacion de receta ", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Calificacion de receta actualizada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "La receta/usuario no fue encontrado"),

    })
    public ResponseEntity<RecipeRatingVo> updateRecipeRating(@RequestBody RecipeRatingVo recipeRatingVo) throws UserNotFoundException, RecipeNotFoundException, RatingIsLowerThanZeroException, RatingIsNullException {
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeRatingService.saveOrUpdateRecipeRating(recipeRatingVo).toVO());
    }

    private List<RecipeRatingVo> transformListToVoList(List<RecipeRating> list){
        List<RecipeRatingVo> result = new ArrayList<>();
        for(RecipeRating obj: list){
            result.add(obj.toVO());
        }
        return result;
    }

}
