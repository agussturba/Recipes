package com.uade.recipes.controllers;

import com.uade.recipes.exceptions.ingredientExceptions.IngredientNotFoundException;
import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;
import com.uade.recipes.model.Dish;
import com.uade.recipes.model.FavoriteRecipe;
import com.uade.recipes.service.favoriteRecipe.FavoriteRecipeService;
import com.uade.recipes.vo.DishVo;
import com.uade.recipes.vo.FavoriteRecipeVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/favorite")
public class FavoriteRecipeController {
    private final FavoriteRecipeService favoriteRecipeService;

    public FavoriteRecipeController(FavoriteRecipeService favoriteRecipeService) {
        this.favoriteRecipeService = favoriteRecipeService;
    }

    @GetMapping
    @ApiOperation(value = "Obtener una lista de todos las recetas favoritas de un usuario ", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de recetas retornados satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "usuario no encontrado")
    })
    public ResponseEntity<List<FavoriteRecipeVo>> getAllFavoritesRecipesByUserId(@PathVariable Integer userId) throws UserNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(transformListToVoList(favoriteRecipeService.getAllFavoritesRecipesByUserId(userId)));
    }

    @PostMapping
    @ApiOperation(value = "Guardar una receta como favorita para un usuario determinado ", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La receta a sido añadida como favorita para el usuario"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "Usuario no encontrado o Receta no encontrada")
    })
    public ResponseEntity<FavoriteRecipeVo> saveFavoriteRecipe(@RequestParam Integer recipeId, @RequestParam Integer userId) throws UserNotFoundException, IngredientNotFoundException, RecipeNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(favoriteRecipeService.saveFavoriteRecipe(recipeId, userId).toVo());
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "Eliminar una receta de favoritos por id usuario y id receta ", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La receta a sido eliminada de favoritos para el usuario indicado"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "Usuario no encontrado o Receta no encontrada")
    })
    public ResponseEntity deleteFavoriteRecipeByUserIdAndRecipeId(@RequestParam Integer recipeId, @RequestParam Integer userId) throws UserNotFoundException, IngredientNotFoundException, RecipeNotFoundException {
        favoriteRecipeService.deleteFavoriteRecipeByUserIdAndRecipeId(recipeId, userId);
        return (ResponseEntity) ResponseEntity.status(HttpStatus.OK);
    }
    @DeleteMapping
    @ApiOperation(value = "Eliminar una receta de favoritos por su id ", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La receta a sido eliminada de favoritos para el usuario indicado"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "Usuario no encontrado o Receta no encontrada")
    })
    public ResponseEntity deleteFavoriteRecipeByFavoriteRecipeId(@RequestParam Integer favoriteRecipeId) throws UserNotFoundException, IngredientNotFoundException, RecipeNotFoundException {
        favoriteRecipeService.deleteFavoriteRecipeByFavoriteRecipeId(favoriteRecipeId);
        return (ResponseEntity) ResponseEntity.status(HttpStatus.OK);
    }
    private List<FavoriteRecipeVo> transformListToVoList(List<FavoriteRecipe> list){
        List<FavoriteRecipeVo> result = new ArrayList<>();
        for(FavoriteRecipe favoriteRecipe : list){
            result.add(favoriteRecipe.toVo());
        }
        return result;
    }
}
