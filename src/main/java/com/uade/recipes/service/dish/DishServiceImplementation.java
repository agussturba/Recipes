package com.uade.recipes.service.dish;

import com.uade.recipes.exceptions.dishExceptions.DishNameContainsNumberException;
import com.uade.recipes.exceptions.dishExceptions.DishNameExistsException;
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
    public List<Dish> getDishesByTypeIdList(List<Integer> typeIdList) {
        List<Type> types = typeService.getTypesByIdList(typeIdList);
        List<Dish> candidateDishes = dishRepository.findByTypesIsIn(types);
        return filterDishesByTypesIdList(candidateDishes, typeIdList);
    }

    @Override
    public Dish saveOrUpdateDish(DishVo dishVo) throws DishNameContainsNumberException, DishTypeContainsNumberException {
        if (dishVo.getId() == null) {
            dishExists(dishVo);
        }
        validateDishData(dishVo);
        Set<Type> types = getListOfTypes(dishVo.getTypesIdList());
        return dishRepository.save(dishVo.toModel(types));
    }

    private Set<Type> getListOfTypes(List<Integer> typesIdList) {
        Set<Type> types = new HashSet<>();
        for (Integer typeId :
                typesIdList) {
            types.add(typeService.getTypeById(typeId));
        }
        return types;
    }

    private List<Dish> filterDishesByTypesIdList(List<Dish> candidateDishes, List<Integer> typeIdList) {
        for (Dish dish : candidateDishes) {
            for (Type type : dish.getTypes()) {
                if (!typeIdList.contains(type.getId())) {
                    candidateDishes.remove(dish);
                    break;
                }
            }
        }
        return candidateDishes;
    }

    private void dishExists(DishVo dishVo) {
        try {
            this.getDishByName(dishVo.getName());
            throw new DishNameExistsException();
        } catch (DishNotFoundException ignored) {
        }
    }
}
