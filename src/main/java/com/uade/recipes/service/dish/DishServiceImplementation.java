package com.uade.recipes.service.dish;

import com.uade.recipes.exceptions.dishExceptions.DishNameContainsNumberException;
import com.uade.recipes.exceptions.dishExceptions.DishNameExistsException;
import com.uade.recipes.exceptions.dishExceptions.DishNotFoundException;
import com.uade.recipes.exceptions.dishExceptions.DishTypeContainsNumberException;
import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.model.Dish;
import com.uade.recipes.model.Recipe;
import com.uade.recipes.model.Type;
import com.uade.recipes.persistance.DishRepository;
import com.uade.recipes.persistance.RecipeRepository;
import com.uade.recipes.service.type.TypeService;
import com.uade.recipes.vo.DishVo;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.uade.recipes.validations.DishesValidations.validateDishData;

@Service
public class DishServiceImplementation implements DishService {
    private final DishRepository dishRepository;
    private final TypeService typeService;
    private final RecipeRepository recipeRepository;

    public DishServiceImplementation(DishRepository dishRepository, TypeService typeService, RecipeRepository recipeRepository) {
        this.dishRepository = dishRepository;
        this.typeService = typeService;
        this.recipeRepository = recipeRepository;
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
    public Dish getDishByRecipeId(Integer recipeId) throws RecipeNotFoundException {
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(RecipeNotFoundException::new);
        return dishRepository.findByRecipes(recipe);
    }

    @Override
    public List<Dish> getDishesByTypeIdList(List<Integer> typeIdList) {
        List<Type> types = typeService.getTypesByIdList(typeIdList);
        List<Dish> candidateDishes = dishRepository.findByTypeIsIn(types);
        return filterDishesByTypesIdList(candidateDishes, typeIdList);
    }

    @Override
    public Dish saveOrUpdateDish(DishVo dishVo) throws DishNameContainsNumberException, DishTypeContainsNumberException {
        if (dishVo.getId() == null) {
            dishExists(dishVo);
        }
        validateDishData(dishVo);
        Type type = typeService.getTypeById(dishVo.getTypeId());
        return dishRepository.save(dishVo.toModel(type));
    }


    private List<Dish> filterDishesByTypesIdList(List<Dish> candidateDishes, List<Integer> typeIdList) {
        for (Dish dish : candidateDishes) {
                if (!typeIdList.contains(dish.getType().getId())) {
                    candidateDishes.remove(dish);
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
