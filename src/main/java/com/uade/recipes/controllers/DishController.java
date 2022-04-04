package com.uade.recipes.controllers;

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
    public ResponseEntity<List<Dish>> getAllDishes(@RequestParam(required = false) String type) {
        if (type != null) {
            return ResponseEntity.status(HttpStatus.OK).body(dishService.getDishesByLabel(type));
        }
        return ResponseEntity.status(HttpStatus.OK).body(dishService.getAllDishes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dish> getDishById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(dishService.getDishById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Dish> getDishByName(@PathVariable String name) {
        return ResponseEntity.status(HttpStatus.OK).body(dishService.getDishByName(name));
    }

    @PostMapping
    public ResponseEntity<Dish> saveDish(@RequestBody DishVo dishVo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(dishService.saveOrUpdateDish(dishVo));
    }

    @PutMapping
    public ResponseEntity<Dish> updateDish(@RequestBody DishVo dishVo) {
        return ResponseEntity.status(HttpStatus.OK).body(dishService.saveOrUpdateDish(dishVo));
    }

}
