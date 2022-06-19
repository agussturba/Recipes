package com.uade.recipes.controllers;

import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.exceptions.recipeRatingExceptions.RatingIsLowerThanZeroException;
import com.uade.recipes.exceptions.recipeRatingExceptions.RatingIsNullException;
import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;

import com.uade.recipes.model.RecipeRating;
import com.uade.recipes.service.recipeRating.RecipeRatingService;
import com.uade.recipes.vo.RecipeRatingVo;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rating")
public class RecipeRatingController {
    private final RecipeRatingService recipeRatingService;

    public RecipeRatingController(RecipeRatingService recipeRatingService) {
        this.recipeRatingService = recipeRatingService;
    }

    @GetMapping("/{recipeRatingId}")
    @ApiOperation(value = "Obtener una calificación de una receta por el ID de dicha calificación", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Calificación de receta retornada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "La calificación de receta no fue encontrada"),

    })
    public ResponseEntity<List<RecipeRatingVo>> getRecipeRatingById(@PathVariable Integer recipeRatingId) throws RecipeNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(transformListToVoList(recipeRatingService.getRecipeRatingByRecipeId(recipeRatingId)));
    }

    @GetMapping("/amount/{recipeId}")
    @ApiOperation(value = "Obtener la cantidad de calificaciones de una receta", response = Integer.class)
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
    @ApiOperation(value = "Crear una nueva calificación para una receta ", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Calificación de receta creada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "La receta/usuario no fue encontrado")

    })
    public ResponseEntity<RecipeRatingVo> saveRecipeRating(@RequestBody RecipeRatingVo recipeRatingVo) throws UserNotFoundException, RecipeNotFoundException, RatingIsLowerThanZeroException, RatingIsNullException {
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeRatingService.saveOrUpdateRecipeRating(recipeRatingVo).toVO());
    }

    @PutMapping
    @ApiOperation(value = "Actualizar la calificación de una receta ", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Calificación de receta actualizada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "La receta/usuario no fue encontrado"),

    })
    public ResponseEntity<RecipeRatingVo> updateRecipeRating(@RequestBody RecipeRatingVo recipeRatingVo) throws UserNotFoundException, RecipeNotFoundException, RatingIsLowerThanZeroException, RatingIsNullException {
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeRatingService.saveOrUpdateRecipeRating(recipeRatingVo).toVO());
    }

    @GetMapping("/user")
    @ApiOperation(value = "Obtener una calificación de una receta por el userId y la recetaId", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Calificación de receta retornada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "La calificación de receta no fue encontrada o "),

    })
    public ResponseEntity<RecipeRatingVo> getRecipeRatingByRecipeIdAndUserId(@RequestParam Integer recipeId,@RequestParam Integer userId) throws RecipeNotFoundException, UserNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(recipeRatingService.getRecipeRatingByRecipeIdAndUserId(recipeId,userId).toVO());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Double> getAverageRatingByUser(@PathVariable Integer userId) throws UserNotFoundException, RecipeNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(recipeRatingService.getAverageOfRecipeRatingsByUser(userId));
    }

    private List<RecipeRatingVo> transformListToVoList(List<RecipeRating> list) {
        return list.stream().map(RecipeRating::toVO).collect(Collectors.toList());
    }

}
