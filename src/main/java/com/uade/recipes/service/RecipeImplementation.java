package com.uade.recipes.service;

import com.uade.recipes.exceptions.RecipeNotFoundException;
import com.uade.recipes.exceptions.dishExceptions.DishNotFoundException;
import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;
import com.uade.recipes.model.Dish;
import com.uade.recipes.model.Recipe;
import com.uade.recipes.model.User;
import com.uade.recipes.persistance.DishRepository;
import com.uade.recipes.persistance.RecipeRepository;
import com.uade.recipes.persistance.UserRepository;
import com.uade.recipes.vo.RecipeVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeImplementation implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final DishRepository dishRepository;

    public RecipeImplementation(RecipeRepository recipeRepository, UserRepository userRepository, DishRepository dishRepository) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        this.dishRepository = dishRepository;
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return (List<Recipe>) recipeRepository.findAll();
    }

    @Override
    public Recipe getRecipeById(Integer recipeId) {
        return recipeRepository.findById(recipeId).orElseThrow(RecipeNotFoundException::new);
    }

    @Override
    public Recipe getRecipeByName(String recipeName) {
        return recipeRepository.findByName(recipeName).orElseThrow(RecipeNotFoundException::new);
    }

    @Override
    public List<Recipe> getRecipeByHavingGreaterRating(Double rating) {
        return recipeRepository.findByRatingGreaterThanEqual(rating);
    }

    @Override
    public List<Recipe> getRecipesByUserId(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return recipeRepository.findByUser(user);
    }

    @Override
    public List<Recipe> getRecipesByDishId(Integer dishId) {
        Dish dish = dishRepository.findById(dishId).orElseThrow(UserNotFoundException::new);
        return recipeRepository.findByDish(dish);
    }

    @Override
    public List<Recipe> getRecipeByType(String type) {
        return recipeRepository.findByType(type);
    }

    @Override
    public Recipe saveOrUpdateRecipe(RecipeVo recipeVo) {//TODO THE VALIDATIONS AND THE INGREDIENT QUANTITY PROBLEM
        if (recipeVo.getId()!=null){
            this.getRecipeById(recipeVo.getId());
        }
        User user = userRepository.findById(recipeVo.getUserId()).orElseThrow(UserNotFoundException::new);
        Dish dish = dishRepository.findById(recipeVo.getDishId()).orElseThrow(DishNotFoundException::new);
        Recipe recipe = new Recipe(recipeVo.getName(),recipeVo.getDescription(), recipeVo.getPhotos(), recipeVo.getRating(), null,recipeVo.getInstructions(),dish,user);
        return recipeRepository.save(recipe);
    }
}
