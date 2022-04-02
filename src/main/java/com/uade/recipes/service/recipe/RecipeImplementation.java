package com.uade.recipes.service.recipe;

import com.uade.recipes.exceptions.InstructionNotFoundException;
import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.exceptions.dishExceptions.DishNotFoundException;
import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;
import com.uade.recipes.model.*;
import com.uade.recipes.persistance.*;
import com.uade.recipes.vo.RecipeVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        return recipeRepository.findAll();
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
    public List<Recipe> getRecipesByUserId(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return recipeRepository.findByUser(user);
    }

    @Override
    public List<Recipe> getRecipesByLabels(List<String> labels) {//TODO BUSCAR UN METODO MAS PERFORMATE(POSIBLEMENT UNA QUERY)
        return null;//TODO HACELO FRANCO,MAESTRO DE MONGO,EL KING,EL EMPEREDARO
    }

    @Override
    public List<Recipe> getRecipesByUserIdAndDishId(Integer userId, Integer dishId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Dish dish = dishRepository.findById(dishId).orElseThrow(DishNotFoundException::new);
        return recipeRepository.findByUserAndDish(user, dish);
    }

    @Override
    public List<Recipe> getRecipesByDishId(Integer dishId) {
        Dish dish = dishRepository.findById(dishId).orElseThrow(UserNotFoundException::new);
        return recipeRepository.findByDish(dish);
    }


    @Override
    public Recipe saveOrUpdateRecipe(RecipeVo recipeVo) {//TODO THE VALIDATIONS
        if (recipeVo.getId() != null) {
            this.getRecipeById(recipeVo.getId());
        }
        User user = userRepository.findById(recipeVo.getUserId()).orElseThrow(UserNotFoundException::new);
        Dish dish = dishRepository.findById(recipeVo.getDishId()).orElseThrow(DishNotFoundException::new);
        List<Instruction> instructions = getInstructionsByIds(recipeVo.getInstructions());
        Recipe recipe = new Recipe(recipeVo.getName(), recipeVo.getDescription(), recipeVo.getPhotos(), null, instructions, dish, user);
        Recipe newRecipe = recipeRepository.save(recipe);
        RecipeRating recipeRating = new RecipeRating(newRecipe);
        recipeRatingRepository.save(recipeRating);
        return newRecipe;
    }

    private List<Instruction> getInstructionsByIds(List<Integer> instructionsIds) {
        List<Instruction> instructions = new ArrayList<>();
        for (Integer instructionId : instructionsIds) {
            Instruction instruction = instructionRepository.findById(instructionId).orElseThrow(InstructionNotFoundException::new);
            instructions.add(instruction);
        }
        return instructions;
    }
}
