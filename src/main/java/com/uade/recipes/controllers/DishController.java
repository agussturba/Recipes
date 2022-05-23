package com.uade.recipes.controllers;

import com.uade.recipes.exceptions.dishExceptions.DishNameContainsNumberException;
import com.uade.recipes.exceptions.dishExceptions.DishNotFoundException;
import com.uade.recipes.exceptions.dishExceptions.DishTypeContainsNumberException;
import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.model.Dish;
import com.uade.recipes.service.dish.DishService;
import com.uade.recipes.vo.DishVo;
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
@RequestMapping("/api/dish")
public class DishController {
    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping
    @ApiOperation(value = "Obtener una lista de todos los platos donde se le puede pasar opcionalmente una lisa de tipos", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de platos retornados satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "Tipo/s no encontrado")
    })
    public ResponseEntity<List<DishVo>> getAllDishes(@RequestParam(required = false) List<Integer> typeIds) {
        if (typeIds != null) {
            List<DishVo> result = transformListToVoList(dishService.getDishesByTypeIdList(typeIds));
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        List<DishVo> result = transformListToVoList(dishService.getAllDishes());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Obtener un plato por su ID en la base de datos", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "Plato retornado satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "Plato no encontrado")
    })
    public ResponseEntity<DishVo> getDishById(@PathVariable Integer id) throws DishNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(dishService.getDishById(id).toVO());
    }

    @GetMapping("/name/{name}")
    @ApiOperation(value = "Obtener un plato por su nombre", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Plato retornado satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "Plato no encontrado")
    })
    public ResponseEntity<DishVo> getDishByName(@PathVariable String name) throws DishNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(dishService.getDishByName(name).toVO());
    }

    @GetMapping("/recipe/{recipeId}")
    @ApiOperation(value = "Obtener un plato por el id de una receta", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Plato retornado satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "Plato no encontrado o receta no encontrada")
    })
    public ResponseEntity<DishVo> getDishByRecipeId(@PathVariable Integer recipeId) throws DishNotFoundException, RecipeNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(dishService.getDishByRecipeId(recipeId).toVO());
    }

    @PostMapping
    @ApiOperation(value = "Crear un nuevo plato", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Plato creado satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "Tipo de plato no encontrado")
    })
    public ResponseEntity<DishVo> saveDish(@RequestBody DishVo dishVo) throws DishNameContainsNumberException, DishTypeContainsNumberException {
        return ResponseEntity.status(HttpStatus.CREATED).body(dishService.saveOrUpdateDish(dishVo).toVO());
    }

    @PutMapping
    @ApiOperation(value = "Actualizar un plato", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Plato actualizado satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "Tipo de plato no encontrado")
    })
    public ResponseEntity<DishVo> updateDish(@RequestBody DishVo dishVo) throws DishNameContainsNumberException, DishTypeContainsNumberException {
        return ResponseEntity.status(HttpStatus.OK).body(dishService.saveOrUpdateDish(dishVo).toVO());
    }


    private List<DishVo> transformListToVoList(List<Dish> list) {
        return list.stream().map(Dish::toVO).collect(Collectors.toList());
    }
}
