package com.uade.recipes.service.recipe;

import com.uade.recipes.exceptions.dishExceptions.DishNotFoundException;
import com.uade.recipes.exceptions.ingredientExceptions.CannotDivideTheIngredientException;
import com.uade.recipes.exceptions.ingredientExceptions.IngredientNotFoundException;
import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;
import com.uade.recipes.model.*;
import com.uade.recipes.persistance.RecipeRepository;
import com.uade.recipes.persistance.Recipe_AdditionRepository;
import com.uade.recipes.service.dish.DishService;
import com.uade.recipes.service.ingredientQuantity.IngredientQuantityService;
import com.uade.recipes.service.type.TypeService;
import com.uade.recipes.service.user.UserService;
import com.uade.recipes.vo.RecipeVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.uade.recipes.validations.RecipeValidations.validateRecipeData;

@Service
public class RecipeImplementation implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final Recipe_AdditionRepository recipe_AdditionRepository;
    private final UserService userService;
    private final DishService dishService;
    private final TypeService typeService;
    private final IngredientQuantityService ingredientQuantityService;

    public RecipeImplementation(RecipeRepository recipeRepository, Recipe_AdditionRepository recipe_AdditionRepository, UserService userService, DishService dishService, TypeService typeService, IngredientQuantityService ingredientQuantityService) {
        this.recipeRepository = recipeRepository;
        this.recipe_AdditionRepository = recipe_AdditionRepository;
        this.userService = userService;
        this.dishService = dishService;
        this.typeService = typeService;
        this.ingredientQuantityService = ingredientQuantityService;
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
    public List<Recipe> getRecipesByTypes(List<Integer> typesIds) {
        List<Type> type = typeService.getTypesByIdList(typesIds);
        return recipeRepository.findByTypeInOrderByName(type);
    }

    @Override
    /*
      all the recipes that doesn't have that ingredient
     */
    public List<Recipe> getRecipesByMissingIngredientId(Integer ingredientId) throws IngredientNotFoundException {
        Set<Recipe> unAcceptableRecipes = ingredientQuantityService.getRecipesByIngredientId(ingredientId);
        List<Recipe> recipes = getAllRecipes();
        for (Recipe unAcceptableRecipe : unAcceptableRecipes
        ) {
            recipes.remove(unAcceptableRecipe);
        }
        return recipes;
    }

    @Override
    public List<Recipe> getRecipesByUserIdAndPeopleAmount(Integer userId, Integer peopleAmount) throws UserNotFoundException {
        User user = userService.getUserById(userId);
        return recipeRepository.findByUserAndPeopleAmount(user, peopleAmount);
    }

    @Override
    public List<Recipe> getRecipesByUserIdAndDishIdAndPeopleAmount(Integer userId, Integer dishId, Integer peopleAmount) throws DishNotFoundException {
        Dish dish = dishService.getDishById(dishId);
        List<Recipe> recipes = dish.getRecipes();
        return recipes.stream()
                .filter(recipe -> Objects.equals(recipe.getPeopleAmount(), peopleAmount) && Objects.equals(recipe.getUserId(), userId))
                .collect(Collectors.toList());

    }

    @Override
    public List<Recipe> getRecipesByDishIdAndPeopleAmount(Integer dishId, Integer peopleAmount) throws DishNotFoundException {
        Dish dish = dishService.getDishById(dishId);
        List<Recipe> recipes = dish.getRecipes();
        return recipes.stream()
                .filter(recipe -> Objects.equals(recipe.getPeopleAmount(), peopleAmount))
                .collect(Collectors.toList());

    }


    @Override
    public List<Recipe> getRecipesByUserIdAndDishId(Integer userId, Integer dishId) throws DishNotFoundException {
        Dish dish = dishService.getDishById(dishId);
        List<Recipe> recipes = dish.getRecipes();
        return recipes.stream()
                .filter(recipe -> Objects.equals(recipe.getUserId(), userId))
                .collect(Collectors.toList());


    }

    @Override
    public List<Recipe> getRecipesByDishId(Integer dishId) throws DishNotFoundException {
        Dish dish = dishService.getDishById(dishId);
        return dish.getRecipes();
    }

    @Override
    public Boolean isRecipeEnabled(Integer recipeId) throws RecipeNotFoundException {
        Recipe recipe = getRecipeById(recipeId);
        Recipe_Addition recipe_Addition = recipe_AdditionRepository.findByRecipe(recipe);
        return recipe_Addition.isEnabled();
    }


    @Override
    public Recipe saveOrUpdateRecipe(RecipeVo recipeVo) throws DishNotFoundException, UserNotFoundException {
        validateRecipeData(recipeVo);
        User user = userService.getUserById(recipeVo.getUserId());
        Recipe recipe = recipeRepository.save(recipeVo.toModel(user));
        saveAdditionalInformation(recipeVo, recipe);
        return recipe;
    }

    @Override
    public List<IngredientQuantity> convertRecipeIngredientQuantityByIngredientIdAndRecipeIdAndNewQuantity(Integer ingredientId, Double newQuantity, Integer recipeId) throws IngredientNotFoundException, RecipeNotFoundException, CannotDivideTheIngredientException {
        IngredientQuantity ingredientQuantity = ingredientQuantityService.getIngredientQuantityByIngredientIdAndRecipeId(ingredientId, recipeId);
        Double conversionFactor = getConversionFactor(ingredientQuantity.getQuantity(), newQuantity);
        return ingredientQuantityService.getConvertedIngredientQuantityListByRecipeIdAndConversionFactor(recipeId, conversionFactor);
    }

    @Override
    public List<IngredientQuantity> convertRecipeIngredientQuantityByConversionFactor(Integer recipeId, Double conversionFactor) throws RecipeNotFoundException, CannotDivideTheIngredientException, IngredientNotFoundException {
        return ingredientQuantityService.getConvertedIngredientQuantityListByRecipeIdAndConversionFactor(recipeId, conversionFactor);
    }


    private Double getConversionFactor(Double oldQuantity, Double newQuantity) {
        return newQuantity / oldQuantity;
    }

    private void saveAdditionalInformation(RecipeVo recipeVo, Recipe recipe) {
        Recipe_Addition recipe_addition = recipeVo.getAdditionalInformation();
        recipe_addition.setRecipe(recipe);
        recipe_AdditionRepository.save(recipe_addition);
    }


}
