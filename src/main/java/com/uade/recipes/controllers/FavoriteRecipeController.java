package com.uade.recipes.controllers;

import com.uade.recipes.exceptions.ingredientExceptions.IngredientNotFoundException;
import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;
import com.uade.recipes.model.FavoriteRecipe;
import com.uade.recipes.service.favoriteRecipe.FavoriteRecipeService;
import com.uade.recipes.vo.FavoriteRecipeVo;
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

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/favorite")
public class FavoriteRecipeController {
    private final FavoriteRecipeService favoriteRecipeService;

    public FavoriteRecipeController(FavoriteRecipeService favoriteRecipeService) {
        this.favoriteRecipeService = favoriteRecipeService;
    }

    @GetMapping("/{userId}")
    @ApiOperation(value = "Obtener una lista de todos las recetas favoritas de un usuario", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de recetas favoritas del usuario retornado satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "Usuario no encontrado")
    })
    public ResponseEntity<List<FavoriteRecipeVo>> getAllFavoritesRecipesByUserId(@PathVariable Integer userId) throws UserNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(transformListToVoList(favoriteRecipeService.getAllFavoritesRecipesByUserId(userId)));
    }

    @PostMapping
    @ApiOperation(value = "Guardar una receta como favorita para un usuario determinado ", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "La receta a sido añadida como favorita para el usuario"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "Usuario no encontrado o Receta no encontrada")
    })
    public ResponseEntity<FavoriteRecipeVo> saveFavoriteRecipe(@RequestParam Integer recipeId, @RequestParam Integer userId) throws UserNotFoundException, IngredientNotFoundException, RecipeNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(favoriteRecipeService.saveFavoriteRecipe(recipeId, userId).toVo());
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "Eliminar una receta de favoritos por id usuario y id receta", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La receta a sido eliminada de favoritos para el usuario indicado"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "Usuario no encontrado o Receta no encontrada")
    })
    public ResponseEntity deleteFavoriteRecipeByUserIdAndRecipeId(@RequestParam Integer recipeId, @RequestParam Integer userId) throws UserNotFoundException, IngredientNotFoundException, RecipeNotFoundException {
        favoriteRecipeService.deleteFavoriteRecipeByUserIdAndRecipeId(recipeId, userId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping
    @ApiOperation(value = "Eliminar una receta de favoritos por su id ", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "La receta a sido eliminada de favoritos para el usuario indicado"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "Usuario no encontrado o Receta no encontrada")
    })
    public ResponseEntity deleteFavoriteRecipeByFavoriteRecipeId(@RequestParam Integer favoriteRecipeId) throws UserNotFoundException, IngredientNotFoundException, RecipeNotFoundException {
        favoriteRecipeService.deleteFavoriteRecipeByFavoriteRecipeId(favoriteRecipeId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("isfavourite")
    @ApiOperation(value = "Indica si una receta pertenece a la lista de favoritos de un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La receta existe/no existe en la lista de favoritos"),
            @ApiResponse(code = 404, message = "Usuario no encontrado o receta no encontrada"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
    })
    public ResponseEntity<Boolean> isInFavourites(@RequestParam Integer recipeId, @RequestParam Integer userId) throws UserNotFoundException, RecipeNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(favoriteRecipeService.isInFavourites(recipeId, userId));
    }

    private List<FavoriteRecipeVo> transformListToVoList(List<FavoriteRecipe> list) {
        return list.stream().map(FavoriteRecipe::toVo).collect(Collectors.toList());
    }
}
