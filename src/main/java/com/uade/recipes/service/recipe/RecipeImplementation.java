package com.uade.recipes.service.recipe;


import com.uade.recipes.exceptions.ingredientExceptions.CannotDivideTheIngredientException;
import com.uade.recipes.exceptions.ingredientExceptions.IngredientNotFoundException;
import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;
import com.uade.recipes.model.IngredientQuantity;
import com.uade.recipes.model.Recipe;
import com.uade.recipes.model.Type;
import com.uade.recipes.model.User;
import com.uade.recipes.persistance.RecipeRepository;
import com.uade.recipes.service.ingredientQuantity.IngredientQuantityService;
import com.uade.recipes.service.type.TypeService;
import com.uade.recipes.service.user.UserService;
import com.uade.recipes.utilities.SetsUtilities;
import com.uade.recipes.vo.RecipeVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.uade.recipes.utilities.SetsUtilities.intersectionSet;
import static com.uade.recipes.utilities.SetsUtilities.mergeSet;
import static com.uade.recipes.validations.RecipeValidations.validateRecipeData;

@Service
public class RecipeImplementation implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final UserService userService;
    private final TypeService typeService;
    private final IngredientQuantityService ingredientQuantityService;

    public RecipeImplementation(RecipeRepository recipeRepository, UserService userService, TypeService typeService, IngredientQuantityService ingredientQuantityService) {
        this.recipeRepository = recipeRepository;
        this.userService = userService;
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
    public List<Recipe> getRecipesByOwnerId(Integer ownerId) throws UserNotFoundException {
        User owner = userService.getUserById(ownerId);
        return recipeRepository.findByOwnerOrderByName(owner);
    }

    @Override
    public List<Recipe> getRecipesByTypes(List<Integer> typesIds) {
        List<Type> type = typeService.getTypesByIdList(typesIds);
        return recipeRepository.findByTypeInOrderByName(type);
    }

    @Override
    public Set<Recipe> getRecipesByTypesAndIngredients(List<Integer> typesIds, List<Integer> ingredientsIds) throws IngredientNotFoundException {
        Set<Recipe> typesRecipe = new HashSet<>(this.getRecipesByTypes(typesIds));
        Set<Recipe> ingredientsRecipe = this.getRecipesByIngredients(ingredientsIds);
        return intersectionSet(typesRecipe, ingredientsRecipe);
    }

    @Override
    public Set<Recipe> getRecipesByTypesAndExcludedIngredients(List<Integer> typesIds, List<Integer> ingredientsIds) throws IngredientNotFoundException {
        Set<Recipe> typesRecipe = new HashSet<>(this.getRecipesByTypes(typesIds));
        Set<Recipe> ingredientsRecipe = this.getRecipesByMissingIngredientIdList(ingredientsIds);
        return intersectionSet(typesRecipe, ingredientsRecipe);
    }

    @Override
    public Set<Recipe> getRecipesByIngredients(List<Integer> ingredientsIds) throws IngredientNotFoundException {
        Set<Recipe> recipeSet = new HashSet<>();
        for (Integer ingredientId : ingredientsIds) {
            if (recipeSet.isEmpty()){
                recipeSet.addAll(ingredientQuantityService.getRecipesByIngredientId(ingredientId));
            }
            else{
                recipeSet = intersectionSet(recipeSet,ingredientQuantityService.getRecipesByIngredientId(ingredientId));
            }
        }
        return recipeSet;
    }


    public Set<Recipe> getRecipesByMissingIngredientId(Integer ingredientId) throws IngredientNotFoundException {
        Set<Recipe> unAcceptableRecipes = ingredientQuantityService.getRecipesByIngredientId(ingredientId);
        List<Recipe> recipes = getAllRecipes();
        return recipes.stream().filter(unAcceptableRecipes::contains).collect(Collectors.toSet());

    }

    @Override
    public Set<Recipe> getRecipesByMissingIngredientIdList(List<Integer> ingredientIds) throws IngredientNotFoundException {
        Set<Recipe> recipeSet = new HashSet<>();
        for (Integer ingredientId : ingredientIds) {
            recipeSet = mergeSet(recipeSet, getRecipesByMissingIngredientId(ingredientId));
        }
        return recipeSet;
    }

    @Override
    public Set<Recipe> getRecipesByIncludedIngredientsAndExcludedIngredients(List<Integer> includedIngredientsIds, List<Integer> excludedIngredientsIds) throws IngredientNotFoundException {
        Set<Recipe> recipesByMissingIngredientList = this.getRecipesByMissingIngredientIdList(excludedIngredientsIds);
        Set<Recipe> recipesByIngredients = this.getRecipesByIngredients(includedIngredientsIds);
        recipesByIngredients.removeAll(recipesByMissingIngredientList);
        return recipesByIngredients;
    }

    @Override
    public Set<Recipe> getRecipesByIncludedIngredientsAndExcludedIngredientsAndTypes(List<Integer> includedIngredientsIds, List<Integer> excludedIngredientsIds, List<Integer> typesIds) throws IngredientNotFoundException {
        Set<Recipe> recipesByMissingIngredientList = this.getRecipesByMissingIngredientIdList(excludedIngredientsIds);
        Set<Recipe> recipesByIngredients = this.getRecipesByIngredients(includedIngredientsIds);
        Set<Recipe> typesRecipe = new HashSet<>(this.getRecipesByTypes(typesIds));
        recipesByIngredients.removeAll(recipesByMissingIngredientList);
        return intersectionSet(recipesByIngredients, typesRecipe);
    }

    @Override
    public List<Recipe> getRecipesByOwnerIdAndPeopleAmount(Integer ownerId, Integer peopleAmount) throws UserNotFoundException {
        User owner = userService.getUserById(ownerId);
        return recipeRepository.findByOwnerAndPeopleAmount(owner, peopleAmount);
    }

    @Override
    public Integer getAmountOfRecipesByOwnerId(Integer ownerId) throws UserNotFoundException {
        userService.getUserById(ownerId);
        return this.getRecipesByOwnerId(ownerId).size();
    }


    @Override
    public Boolean isRecipeEnabled(Integer recipeId) throws RecipeNotFoundException {
        Recipe recipe = getRecipeById(recipeId);
        return recipe.isEnabled();
    }


    @Override
    public Recipe saveOrUpdateRecipe(RecipeVo recipeVo) throws UserNotFoundException {
        validateRecipeData(recipeVo);
        User owner = userService.getUserById(recipeVo.getOwnerId());
        Type type = typeService.getTypeById(recipeVo.getTypeId());
        return recipeRepository.save(recipeVo.toModel(owner, type));
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

    @Override
    public List<Recipe> findRecipesByPartialName(String name) throws RecipeNotFoundException {
        return recipeRepository.findByNameContainingIgnoreCase(name).orElseThrow(RecipeNotFoundException::new);
    }

    @Override
    public List<Recipe> findRecipesByPartialUsername(String username) throws UserNotFoundException {
        List<User> users = userService.getUsersByPartialUserName(username);
        List<Recipe> recipes = new ArrayList<>();
        for (User user : users) {
            recipes.addAll(getRecipesByOwnerId(user.getId()));
        }
        return recipes;
    }


    private Double getConversionFactor(Double oldQuantity, Double newQuantity) {
        return newQuantity / oldQuantity;
    }


}
