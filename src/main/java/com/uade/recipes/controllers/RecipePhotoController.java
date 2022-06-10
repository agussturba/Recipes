package com.uade.recipes.controllers;

import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.model.RecipePhoto;
import com.uade.recipes.service.recipePhoto.RecipePhotoService;
import com.uade.recipes.vo.RecipePhotoVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recipePhotos")
public class RecipePhotoController {
    private final RecipePhotoService recipePhotoService;

    public RecipePhotoController(RecipePhotoService recipePhotoService) {
        this.recipePhotoService = recipePhotoService;
    }


    @GetMapping("/{id}")
    @ApiOperation(value = "Obtener una foto de receta por ID", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "Foto de receta retornada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "La foto de receta no fue encontrada"),

    })
    public ResponseEntity<RecipePhotoVo> getRecipePhotoById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(recipePhotoService.getRecipePhotoById(id).toVO());
    }

    @GetMapping("/recipe/{recipeId}")
    @ApiOperation(value = "Obtener fotos de receta por receta", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "Foto de receta retornada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "La receta no fue encontrada"),

    })
    public ResponseEntity<List<RecipePhotoVo>> getRecipePhotosByRecipe(@PathVariable Integer recipeId) throws RecipeNotFoundException {
        List<RecipePhotoVo> result = transformListToVoList(recipePhotoService.getRecipePhotosByRecipeId(recipeId));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping
    @ApiOperation(value = "Crear una o mas fotos para una receta", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "Foto de receta creada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "La receta no fue encontrada"),

    })
    public ResponseEntity<List<RecipePhotoVo>> saveRecipePhoto(@RequestParam Integer recipeId, @RequestParam List<MultipartFile> images) throws RecipeNotFoundException, IOException {
        List<RecipePhotoVo> result = transformListToVoList( (List<RecipePhoto>) recipePhotoService.saveRecipePhoto(recipeId, images));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping
    @ApiOperation(value = "Elimina una foto de una receta", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "Foto de receta eliminada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "La receta no fue encontrada")

    })
    public ResponseEntity deleteRecipePhoto(@RequestParam Integer recipeId, @RequestParam Integer recipePhotoId) throws RecipeNotFoundException, IOException {
        recipePhotoService.deleteRecipePhoto(recipeId, recipePhotoId);
        return new ResponseEntity(HttpStatus.OK);
    }

    private List<RecipePhotoVo> transformListToVoList(List<RecipePhoto> list){
        return list.stream().map(RecipePhoto::toVO).collect(Collectors.toList());
    }

}
