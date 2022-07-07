package com.uade.recipes.service.recipe;

import com.uade.recipes.exceptions.ingredientExceptions.CannotDivideTheIngredientException;
import com.uade.recipes.exceptions.ingredientExceptions.IngredientNotFoundException;
import com.uade.recipes.exceptions.instructionExceptions.InstructionNotFoundException;
import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;
import com.uade.recipes.model.IngredientQuantity;
import com.uade.recipes.model.Recipe;
import com.uade.recipes.vo.RecipeVo;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface RecipeService {
    List<Recipe> getAllRecipes();

    Recipe getRecipeById(Integer recipeId) throws RecipeNotFoundException;

    List<Recipe> getRecipesByName(String recipeName);

    List<Recipe> getRecipesByPeopleAmount(Integer peopleAmount);

    List<Recipe> getRecipesByOwnerId(Integer ownerId) throws UserNotFoundException;

    List<Recipe> getRecipesByTypes(List<Integer> typesIds);

    Set<Recipe> getRecipesByTypesAndExcludedIngredients(List<Integer> typesIds, List<Integer> ingredientsIds) throws IngredientNotFoundException;

    Set<Recipe> getRecipesByTypesAndIngredients(List<Integer> typesIds, List<Integer> ingredientsIds) throws IngredientNotFoundException;

    Set<Recipe> getRecipesByIngredients(List<Integer> ingredientsIds) throws IngredientNotFoundException;

    Set<Recipe> getRecipesByMissingIngredientIdList(List<Integer> ingredientIds) throws IngredientNotFoundException;

    Set<Recipe> getRecipesByIncludedIngredientsAndExcludedIngredients(List<Integer> includedIngredientsIds, List<Integer> excludedIngredientsIds) throws IngredientNotFoundException;

    Set<Recipe> getRecipesByIncludedIngredientsAndExcludedIngredientsAndTypes(List<Integer> includedIngredientsIds, List<Integer> excludedIngredientsIds, List<Integer> typesIds) throws IngredientNotFoundException;

    List<Recipe> getRecipesByOwnerIdAndPeopleAmount(Integer ownerId, Integer peopleAmount) throws UserNotFoundException;

    Recipe updateRecipe(RecipeVo recipeVo) throws RecipeNotFoundException;

    Recipe saveRecipe(RecipeVo recipeVo) throws UserNotFoundException;

    Recipe enabledRecipe(Integer recipeId) throws RecipeNotFoundException, UserNotFoundException;

    List<IngredientQuantity> convertRecipeIngredientQuantityByIngredientIdAndRecipeIdAndNewQuantity(Integer ingredientId, Double newQuantity, Integer recipeId) throws IngredientNotFoundException, RecipeNotFoundException, CannotDivideTheIngredientException;

    List<IngredientQuantity> convertRecipeIngredientQuantityByConversionFactor(Integer recipeId, Double conversionFactor) throws IngredientNotFoundException, RecipeNotFoundException, CannotDivideTheIngredientException;

    List<Recipe> findRecipesByPartialName(String name) throws RecipeNotFoundException;

    List<Recipe> findRecipesByPartialUsername(String username) throws UserNotFoundException;

    Integer getAmountOfRecipesByOwnerId(Integer ownerId) throws UserNotFoundException;

    void deleteRecipeByRecipeId(Integer recipeId) throws RecipeNotFoundException, InstructionNotFoundException;

    void deleteMainPhoto(Integer recipeId) throws RecipeNotFoundException, IOException;
}
