package com.uade.recipes.service.dish;

import com.uade.recipes.exceptions.dishExceptions.DishNameContainsNumberException;
import com.uade.recipes.exceptions.dishExceptions.DishNotFoundException;
import com.uade.recipes.exceptions.dishExceptions.DishTypeContainsNumberException;
import com.uade.recipes.model.Dish;
import com.uade.recipes.model.Type;
import com.uade.recipes.persistance.DishRepository;
import com.uade.recipes.service.type.TypeService;
import com.uade.recipes.vo.DishVo;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.uade.recipes.validations.DishesValidations.validateDishData;

@Service
public class DishServiceImplementation implements DishService {
    private final DishRepository dishRepository;
    private final TypeService typeService;

    public DishServiceImplementation(DishRepository dishRepository, TypeService typeService) {
        this.dishRepository = dishRepository;
        this.typeService = typeService;
    }

    @Override
    public List<Dish> getAllDishes() {
        return (List<Dish>) dishRepository.findAll();
    }

    @Override
    public Dish getDishById(Integer dishId) throws DishNotFoundException {
        return dishRepository.findById(dishId).orElseThrow(DishNotFoundException::new);
    }

    @Override
    public Dish getDishByName(String dishName) throws DishNotFoundException {
        return dishRepository.findByName(dishName).orElseThrow(DishNotFoundException::new);
    }

    @Override
    public List<Dish> getDishesByTypeId(Integer typeId) {
        Type type = typeService.getTypeById(typeId);
        return dishRepository.findByTypes(type);
    }

    @Override
    public Dish saveOrUpdateDish(DishVo dishVo) throws DishNameContainsNumberException, DishTypeContainsNumberException {
        validateDishData(dishVo);
        Set<Type> types = getListOfTypes(dishVo.getTypesIdList());
        return dishRepository.save(dishVo.toModel(types));
    }
    private Set<Type> getListOfTypes(List<Integer> typesIdList){
        Set<Type> types = new HashSet<>();
        for (Integer typeId:
                typesIdList) {
            types.add(typeService.getTypeById(typeId));
        }
        return types;
    }
}
