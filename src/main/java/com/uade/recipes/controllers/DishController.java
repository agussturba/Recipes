package com.uade.recipes.controllers;

import com.uade.recipes.exceptions.dishExceptions.DishNameContainsNumberException;
import com.uade.recipes.exceptions.dishExceptions.DishNotFoundException;
import com.uade.recipes.exceptions.dishExceptions.DishTypeContainsNumberException;
import com.uade.recipes.model.Dish;
import com.uade.recipes.service.dish.DishService;
import com.uade.recipes.vo.DishVo;
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
    public ResponseEntity<List<Dish>> getAllDishes(@RequestParam(required = false) Integer typeId) {
        if (typeId != null) {
            return ResponseEntity.status(HttpStatus.OK).body(dishService.getDishesByTypeId(typeId));
        }
        return ResponseEntity.status(HttpStatus.OK).body(dishService.getAllDishes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dish> getDishById(@PathVariable Integer id) throws DishNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(dishService.getDishById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Dish> getDishByName(@PathVariable String name) throws DishNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(dishService.getDishByName(name));
    }

    @PostMapping
    public ResponseEntity<Dish> saveDish(@RequestBody DishVo dishVo) throws DishNameContainsNumberException, DishTypeContainsNumberException {
        return ResponseEntity.status(HttpStatus.CREATED).body(dishService.saveOrUpdateDish(dishVo));
    }

    @PutMapping
    public ResponseEntity<Dish> updateDish(@RequestBody DishVo dishVo) throws DishNameContainsNumberException, DishTypeContainsNumberException {
        return ResponseEntity.status(HttpStatus.OK).body(dishService.saveOrUpdateDish(dishVo));
    }

}
