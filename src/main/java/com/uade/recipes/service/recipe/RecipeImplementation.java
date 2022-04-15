package com.uade.recipes.service.recipe;

import com.uade.recipes.exceptions.dishExceptions.DishNotFoundException;
import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;
import com.uade.recipes.model.*;
import com.uade.recipes.persistance.RecipeRepository;
import com.uade.recipes.service.dish.DishService;
import com.uade.recipes.service.recipePhoto.RecipePhotoService;
import com.uade.recipes.service.type.TypeService;
import com.uade.recipes.service.user.UserService;
import com.uade.recipes.validations.RecipeValidations;
import com.uade.recipes.vo.RecipeVo;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.uade.recipes.validations.RecipeValidations.validateRecipeData;

@Service
public class RecipeImplementation implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final UserService userService;
    private final DishService dishService;
    private final RecipePhotoService recipePhotoService;
    private final TypeService typeService;

    public RecipeImplementation(RecipeRepository recipeRepository, UserService userService, DishService dishService, RecipePhotoService recipePhotoService, TypeService typeService) {
        this.recipeRepository = recipeRepository;
        this.userService = userService;
        this.dishService = dishService;
        this.recipePhotoService = recipePhotoService;
        this.typeService = typeService;
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return (List<Recipe>) recipeRepository.findAll();
    }

    @Override
    public Recipe getRecipeById(Integer recipeId) throws RecipeNotFoundException {
        return recipeRepository.findById(recipeId).orElseThrow(RecipeNotFoundException::new);
    }

    @Override
    public List<Recipe> getRecipesByName(String recipeName) {
        return recipeRepository.findByNameOrderByName(recipeName);
    }

    @Override
    public List<Recipe> getRecipesByPeopleAmount(Integer peopleAmount) {
        return recipeRepository.findByPeopleAmount(peopleAmount);
    }

    @Override
    public List<Recipe> getRecipesByUserId(Integer userId) throws UserNotFoundException {
        User user = userService.getUserById(userId);
        return recipeRepository.findByUserOrderByName(user);
    }

    @Override
    public List<Recipe> getRecipesByTypes(List<Integer> typesIds) {//TODO Test method
        List<Type> type = typeService.getTypesByIdList(typesIds);
        return recipeRepository.findByTypeInOrderByName(type);
    }

    @Override
    public List<Recipe> getRecipesByUserIdAndPeopleAmount(Integer userId, Integer peopleAmount) throws UserNotFoundException {
        User user = userService.getUserById(userId);
        return recipeRepository.findByUserAndPeopleAmount(user, peopleAmount);
    }

    @Override
    public List<Recipe> getRecipesByUserIdAndDishIdAndPeopleAmount(Integer userId, Integer dishId, Integer peopleAmount) throws UserNotFoundException, DishNotFoundException {
        User user = userService.getUserById(userId);
        Dish dish = dishService.getDishById(dishId);
        return recipeRepository.findByDishAndUserAndPeopleAmount(dish, user, peopleAmount);
    }

    @Override
    public List<Recipe> getRecipesByDishIdAndPeopleAmount(Integer dishId, Integer peopleAmount) throws DishNotFoundException {
        Dish dish = dishService.getDishById(dishId);
        return recipeRepository.findByDishAndPeopleAmount(dish, peopleAmount);
    }

    @Override
    public List<Recipe> getRecipesByUserIdAndTypeIds(Integer userId, List<Integer> typeIdList) throws UserNotFoundException {
        User user = userService.getUserById(userId);
        List<Type> types = typeService.getTypesByIdList(typeIdList);
        return recipeRepository.findByUserAndTypeIsIn(user, types);
    }

    @Override
    public List<Recipe> getRecipesByPeopleAmountAndTypeIds(Integer peopleAmount, List<Integer> typeIdList) throws UserNotFoundException {
        List<Type> types = typeService.getTypesByIdList(typeIdList);
        return recipeRepository.findByPeopleAmountAndTypeIsIn(peopleAmount, types);
    }

    @Override
    public List<Recipe> getRecipesByUserIdAndDishId(Integer userId, Integer dishId) throws DishNotFoundException, UserNotFoundException {
        User user = userService.getUserById(userId);
        Dish dish = dishService.getDishById(dishId);
        return recipeRepository.findByUserAndDish(user, dish);
    }

    @Override
    public List<Recipe> getRecipesByDishId(Integer dishId) throws DishNotFoundException {
        Dish dish = dishService.getDishById(dishId);
        return recipeRepository.findByDish(dish);
    }


    @Override
    public Recipe saveOrUpdateRecipe(RecipeVo recipeVo) throws DishNotFoundException, UserNotFoundException {//TODO THE VALIDATIONS
        validateRecipeData(recipeVo);
        User user = userService.getUserById(recipeVo.getUserId());
        Dish dish = dishService.getDishById(recipeVo.getDishId());
        List<RecipePhoto> recipePhotoList = recipePhotoService.getRecipePhotosByIds(recipeVo.getRecipePhotosIds());
        return recipeVo.toModel(user, dish, recipePhotoList);
    }


}
