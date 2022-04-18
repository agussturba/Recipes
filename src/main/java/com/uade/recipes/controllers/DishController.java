package com.uade.recipes.controllers;

import com.uade.recipes.exceptions.dishExceptions.DishNameContainsNumberException;
import com.uade.recipes.exceptions.dishExceptions.DishNotFoundException;
import com.uade.recipes.exceptions.dishExceptions.DishTypeContainsNumberException;
import com.uade.recipes.model.Conversion;
import com.uade.recipes.model.Dish;
import com.uade.recipes.service.dish.DishService;
import com.uade.recipes.vo.ConversionVo;
import com.uade.recipes.vo.DishVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/dish")
public class DishController {
    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping
    @ApiOperation(value = "Obtener una lista de todos los platos ", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de platos retornados satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "Tipo no encontrado")
    })
    public ResponseEntity<List<DishVo>> getAllDishes(@RequestParam(required = false) List<Integer> typeIds) {
        if (typeIds != null) {
            List<DishVo> result = transformListToVoList(dishService.getDishesByTypeId(typeIds));
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        List<DishVo> result = transformListToVoList(dishService.getAllDishes());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Obtener un plato por su ID en base de datos", response = ResponseEntity.class)
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
            @ApiResponse(code = 302, message = "Plato retornado satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "Plato no encontrado")
    })
    public ResponseEntity<DishVo> getDishByName(@PathVariable String name) throws DishNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(dishService.getDishByName(name).toVO());
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
            @ApiResponse(code = 201, message = "Plato actualizado satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "Tipo de plato no encontrado")
    })
    public ResponseEntity<DishVo> updateDish(@RequestBody DishVo dishVo) throws DishNameContainsNumberException, DishTypeContainsNumberException {
        return ResponseEntity.status(HttpStatus.OK).body(dishService.saveOrUpdateDish(dishVo).toVO());
    }


    private List<DishVo> transformListToVoList(List<Dish> list){
        List<DishVo> result = new ArrayList<>();
        for(Dish dish : list){
            result.add(dish.toVO());
        }
        return result;
    }
}
