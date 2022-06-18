package com.uade.recipes.service.recipe;

import com.uade.recipes.exceptions.ingredientExceptions.CannotDivideTheIngredientException;
import com.uade.recipes.exceptions.ingredientExceptions.IngredientNotFoundException;
import com.uade.recipes.exceptions.instructionExceptions.InstructionNotFoundException;
import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;
import com.uade.recipes.model.IngredientQuantity;
import com.uade.recipes.model.Recipe;
import com.uade.recipes.vo.RecipeVo;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecipeService {
    List<Recipe> getAllRecipes();

    Recipe getRecipeById(Integer recipeId) throws RecipeNotFoundException;

    List<Recipe> getRecipesByName(String recipeName);

    List<Recipe> getRecipesByPeopleAmount(Integer peopleAmount);

    List<Recipe> getRecipesByOwnerId(Integer ownerId) throws UserNotFoundException;

    List<Recipe> getRecipesByTypes(List<Integer> typesIds);

    List<Recipe> getRecipesByMissingIngredientId(Integer ingredientId) throws IngredientNotFoundException;

    List<Recipe> getRecipesByOwnerIdAndPeopleAmount(Integer ownerId, Integer peopleAmount) throws UserNotFoundException;

    Boolean isRecipeEnabled(Integer recipeId) throws UserNotFoundException, RecipeNotFoundException;

    Recipe saveOrUpdateRecipe(RecipeVo RecipeVo) throws  InstructionNotFoundException, UserNotFoundException;

    List<IngredientQuantity> convertRecipeIngredientQuantityByIngredientIdAndRecipeIdAndNewQuantity(Integer ingredientId, Double newQuantity, Integer recipeId) throws IngredientNotFoundException, RecipeNotFoundException, CannotDivideTheIngredientException;

    List<IngredientQuantity> convertRecipeIngredientQuantityByConversionFactor(Integer recipeId, Double conversionFactor) throws IngredientNotFoundException, RecipeNotFoundException, CannotDivideTheIngredientException;

    List<Recipe> findRecipesByPartialName(String name) throws RecipeNotFoundException;

    List<Recipe> findRecipesByPartialUsername(String username) throws UserNotFoundException;
}
