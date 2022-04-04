package com.uade.recipes.service.dish;

import com.uade.recipes.exceptions.dishExceptions.DishNotFoundException;
import com.uade.recipes.model.Dish;
import com.uade.recipes.persistance.DishRepository;
import com.uade.recipes.vo.DishVo;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.uade.recipes.validations.DishesValidations.validateDishData;

@Service
public class DishImplementation implements DishService {
    private final DishRepository dishRepository;

    public DishImplementation(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @Override
    public List<Dish> getAllDishes() {
        return (List<Dish>) dishRepository.findAll();
    }

    @Override
    public Dish getDishById(Integer dishId) {
        return dishRepository.findById(dishId).orElseThrow(DishNotFoundException::new);
    }

    @Override
    public Dish getDishByName(String dishName) {
        return dishRepository.findByName(dishName).orElseThrow(DishNotFoundException::new);
    }

    @Override
    public List<Dish> getDishesByLabel(String dishLabel) {
        return dishRepository.findByLabels(dishLabel);
    }

    @Override
    public Dish saveOrUpdateDish(DishVo dishVo) {
        validateDishData(dishVo);
        return dishRepository.save(dishVo.toModel());
    }
}
