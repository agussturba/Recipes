package com.uade.recipes.service.recipe;

import com.uade.recipes.exceptions.dishExceptions.DishNotFoundException;
import com.uade.recipes.exceptions.instructionExceptions.InstructionNotFoundException;
import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;
import com.uade.recipes.model.*;
import com.uade.recipes.persistance.*;
import com.uade.recipes.vo.RecipeVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RecipeImplementation implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final DishRepository dishRepository;
    private final RecipeRatingRepository recipeRatingRepository;
    private final InstructionRepository instructionRepository;

    public RecipeImplementation(RecipeRepository recipeRepository, UserRepository userRepository, DishRepository dishRepository, RecipeRatingRepository ratingRepository, InstructionRepository instructionRepository) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        this.dishRepository = dishRepository;
        this.recipeRatingRepository = ratingRepository;
        this.instructionRepository = instructionRepository;
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
    public List<Recipe> getRecipeByName(String recipeName) {
        return recipeRepository.findByName(recipeName);
    }

    @Override
    public List<Recipe> getRecipesByUserId(Integer userId) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return recipeRepository.findByUser(user);
    }

    @Override
    public Set<Recipe> getRecipesByTypes(List<Type> types) {//TODO Test method
        Set<Recipe> recipes = new HashSet<>();
        for (Type type: types) {
            recipes.addAll(recipeRepository.findRecipeByType(type));
        }
        return recipes;
    }

    @Override
    public List<Recipe> getRecipesByUserIdAndDishId(Integer userId, Integer dishId) throws DishNotFoundException, UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Dish dish = dishRepository.findById(dishId).orElseThrow(DishNotFoundException::new);
        return recipeRepository.findByUserAndDish(user, dish);
    }

    @Override
    public List<Recipe> getRecipesByDishId(Integer dishId) throws UserNotFoundException {
        Dish dish = dishRepository.findById(dishId).orElseThrow(UserNotFoundException::new);
        return recipeRepository.findByDish(dish);
    }


    @Override
    public Recipe saveOrUpdateRecipe(RecipeVo recipeVo) throws DishNotFoundException, InstructionNotFoundException, UserNotFoundException {//TODO THE VALIDATIONS
        User user = userRepository.findById(recipeVo.getUserId()).orElseThrow(UserNotFoundException::new);
        Dish dish = dishRepository.findById(recipeVo.getDishId()).orElseThrow(DishNotFoundException::new);
        //TODO
        return null;
    }

    private List<Instruction> getInstructionsByIds(List<Integer> instructionsIds) throws InstructionNotFoundException {
        List<Instruction> instructions = new ArrayList<>();
        for (Integer instructionId : instructionsIds) {
            Instruction instruction = instructionRepository.findById(instructionId).orElseThrow(InstructionNotFoundException::new);
            instructions.add(instruction);
        }
        return instructions;
    }

}
