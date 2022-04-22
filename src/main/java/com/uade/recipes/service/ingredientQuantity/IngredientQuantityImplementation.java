package com.uade.recipes.service.ingredientQuantity;

import com.uade.recipes.exceptions.ingredientExceptions.CannotDivideTheIngredientException;
import com.uade.recipes.exceptions.ingredientExceptions.IngredientNotFoundException;
import com.uade.recipes.exceptions.ingredientQuantityExceptions.IngredientQuantityNotFoundException;
import com.uade.recipes.exceptions.ingredientQuantityExceptions.UnacceptableQuantityException;
import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.model.*;
import com.uade.recipes.persistance.IngredientQuantityRepository;
import com.uade.recipes.persistance.IngredientRepository;
import com.uade.recipes.persistance.RecipeRepository;
import com.uade.recipes.service.conversion.ConversionService;
import com.uade.recipes.service.ingredient.IngredientService;
import com.uade.recipes.service.unit.UnitService;
import com.uade.recipes.vo.IngredientQuantityVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.uade.recipes.validations.IngredientQuantityValidations.validateIngredientQuantityData;

@Service
public class IngredientQuantityImplementation implements IngredientQuantityService {

    private final IngredientQuantityRepository ingredientQuantityRepository;
    private final IngredientService ingredientService;
    private final UnitService unitService;
    private final RecipeRepository recipeRepository;
    private final ConversionService conversionService;

    public IngredientQuantityImplementation(IngredientQuantityRepository ingredientQuantityRepository, IngredientRepository ingredientRepository, IngredientService ingredientService, UnitService unitService, RecipeRepository recipeRepository, ConversionService conversionService) {
        this.ingredientQuantityRepository = ingredientQuantityRepository;
        this.ingredientService = ingredientService;
        this.unitService = unitService;
        this.recipeRepository = recipeRepository;
        this.conversionService = conversionService;
    }

    @Override
    public List<IngredientQuantity> getAllIngredientQuantity() {
        return (List<IngredientQuantity>) ingredientQuantityRepository.findAll();
    }

    @Override
    public List<IngredientQuantity> getIngredientQuantityByRecipeId(Integer recipeId) throws RecipeNotFoundException {
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(RecipeNotFoundException::new);
        return ingredientQuantityRepository.findByRecipe(recipe);
    }

    @Override
    public List<IngredientQuantity> getConvertedIngredientQuantityListByRecipeIdAndConversionFactor(Integer recipeId, Double conversionFactor) throws RecipeNotFoundException, CannotDivideTheIngredientException, IngredientNotFoundException {
        List<IngredientQuantity> ingredientQuantityList = this.getIngredientQuantityByRecipeId(recipeId);
        List<IngredientQuantity> convertedIngredientQuantityList = new ArrayList<>();
        for (IngredientQuantity ingredientQuantity : ingredientQuantityList) {
            convertedIngredientQuantityList.add(convertIngredientQuantity(ingredientQuantity, conversionFactor));
        }
        return convertedIngredientQuantityList;
    }

    @Override
    public IngredientQuantity getIngredientQuantityById(Integer ingredientQuantityId) throws IngredientQuantityNotFoundException {
        return ingredientQuantityRepository.findById(ingredientQuantityId).orElseThrow(IngredientQuantityNotFoundException::new);
    }

    @Override
    public List<IngredientQuantity> getIngredientQuantityByIngredientId(Integer ingredientId) throws IngredientNotFoundException {
        Ingredient ingredient = ingredientService.getIngredientById(ingredientId).getIngredient();
        return ingredientQuantityRepository.findByIngredient(ingredient);
    }

    @Override
    public IngredientQuantity getIngredientQuantityByIngredientAndQuantity(Ingredient ingredient, Double quantity) throws IngredientQuantityNotFoundException {
        return ingredientQuantityRepository.findByIngredientAndQuantity(ingredient, quantity).orElseThrow(IngredientQuantityNotFoundException::new);
    }

    @Override
    public IngredientQuantity saveOrUpdateIngredientQuantity(IngredientQuantityVo ingredientQuantityVo) throws IngredientNotFoundException, RecipeNotFoundException {
        validateIngredientQuantityData(ingredientQuantityVo);
        Ingredient_Addition ingredientAddition = ingredientService.getIngredientById(ingredientQuantityVo.getIngredientId());

       if (!isAcceptableQuantity(ingredientAddition,ingredientQuantityVo.getQuantity())){
            throw new UnacceptableQuantityException();
        }
        Unit unit = unitService.getUnitById(ingredientQuantityVo.getUnitId());
        Recipe recipe = recipeRepository.findById(ingredientQuantityVo.getRecipeId()).orElseThrow(RecipeNotFoundException::new);
        return ingredientQuantityRepository.save(ingredientQuantityVo.toModel(recipe, ingredientAddition.getIngredient(), unit));
    }

    @Override
    public IngredientQuantityVo convertIngredientQuantityUnitByTargetUnitId(IngredientQuantityVo ingredientQuantityVo, Integer targetUnitId) {
        Conversion conversion = conversionService.getConversionBySourceUnitIdAndTargetUnitId(ingredientQuantityVo.getUnitId(), targetUnitId);
        ingredientQuantityVo.setUnitId(targetUnitId);
        ingredientQuantityVo.setQuantity(ingredientQuantityVo.getQuantity() * conversion.getConversionFactor());
        return ingredientQuantityVo;
    }

    @Override
    public IngredientQuantity getIngredientQuantityByIngredientIdAndRecipeId(Integer ingredientId, Integer recipeId) throws IngredientNotFoundException, RecipeNotFoundException {
        Ingredient_Addition ingredient = ingredientService.getIngredientById(ingredientId);
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(RecipeNotFoundException::new);
        return ingredientQuantityRepository.findByRecipeAndIngredient(recipe, ingredient.getIngredient());
    }

    private IngredientQuantity convertIngredientQuantity(IngredientQuantity ingredientQuantity, Double conversionFactor) throws CannotDivideTheIngredientException, IngredientNotFoundException {
        Double quantity = ingredientQuantity.getQuantity();
        Double newQuantity = quantity * conversionFactor;
        Ingredient_Addition ingredient = ingredientService.getIngredientById(ingredientQuantity.getIngredient().getId());
        if (isAcceptableQuantity(ingredient, newQuantity)) {
            ingredientQuantity.setQuantity(newQuantity);
            return ingredientQuantity;
        } else {
            throw new CannotDivideTheIngredientException();
        }
    }

    private boolean isAcceptableQuantity(Ingredient_Addition addition, Double newQuantity) {
        if (addition.isDividable() || newQuantity % 1 == 0) {
            return true;
        }
        return false;
    }


}
