package com.uade.recipes.controllers;

import com.uade.recipes.exceptions.dishExceptions.DishNameContainsNumberException;
import com.uade.recipes.exceptions.dishExceptions.DishNotFoundException;
import com.uade.recipes.exceptions.dishExceptions.DishTypeContainsNumberException;
import com.uade.recipes.model.Dish;
import com.uade.recipes.service.dish.DishService;
import com.uade.recipes.vo.DishVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dish")
public class DishController {
    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping
    @ApiOperation(value = "Get a list of dishes", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all Dishes"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "Type Not Found")
    })
    public ResponseEntity<List<Dish>> getAllDishes(@RequestParam(required = false) Integer typeId) {
        if (typeId != null) {
            return ResponseEntity.status(HttpStatus.OK).body(dishService.getDishesByTypeId(typeId));
        }
        return ResponseEntity.status(HttpStatus.OK).body(dishService.getAllDishes());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a dish by his db Id", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "Successfully retrieved the Dish"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The dish was not found")
    })
    public ResponseEntity<Dish> getDishById(@PathVariable Integer id) throws DishNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(dishService.getDishById(id));
    }

    @GetMapping("/name/{name}")
    @ApiOperation(value = "Get a dish by name", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "Successfully retrieved the Dish"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The dish was not found")
    })
    public ResponseEntity<Dish> getDishByName(@PathVariable String name) throws DishNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(dishService.getDishByName(name));
    }

    @PostMapping
    @ApiOperation(value = "Create a new dish", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created a new Dish"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The type/s for this dish was not found")
    })
    public ResponseEntity<Dish> saveDish(@RequestBody DishVo dishVo) throws DishNameContainsNumberException, DishTypeContainsNumberException {
        return ResponseEntity.status(HttpStatus.CREATED).body(dishService.saveOrUpdateDish(dishVo));
    }

    @PutMapping
    @ApiOperation(value = "Update a dish", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated a Dish"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The type/s for this dish was not found")
    })
    public ResponseEntity<Dish> updateDish(@RequestBody DishVo dishVo) throws DishNameContainsNumberException, DishTypeContainsNumberException {
        return ResponseEntity.status(HttpStatus.OK).body(dishService.saveOrUpdateDish(dishVo));
    }

}
