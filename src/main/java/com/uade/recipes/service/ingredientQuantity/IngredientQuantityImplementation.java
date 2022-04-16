package com.uade.recipes.service.ingredientQuantity;

import com.uade.recipes.exceptions.ingredientExceptions.IngredientNotFoundException;
import com.uade.recipes.exceptions.ingredientQuantityExceptions.IngredientQuantityNotFoundException;
import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.model.Ingredient;
import com.uade.recipes.model.IngredientQuantity;
import com.uade.recipes.model.Recipe;
import com.uade.recipes.model.Unit;
import com.uade.recipes.persistance.IngredientQuantityRepository;
import com.uade.recipes.persistance.IngredientRepository;
import com.uade.recipes.service.recipe.RecipeService;
import com.uade.recipes.service.unit.UnitService;
import com.uade.recipes.validations.IngredientQuantityValidations;
import com.uade.recipes.vo.IngredientQuantityVo;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.uade.recipes.validations.IngredientQuantityValidations.validateIngredientQuantityData;

@Service
public class IngredientQuantityImplementation implements IngredientQuantityService {

    private final IngredientQuantityRepository ingredientQuantityRepository;
    private final IngredientRepository ingredientRepository;
    private final UnitService unitService;
    private final RecipeService recipeService;

    public IngredientQuantityImplementation(IngredientQuantityRepository ingredientQuantityRepository, IngredientRepository ingredientRepository, UnitService unitService, RecipeService recipeService) {
        this.ingredientQuantityRepository = ingredientQuantityRepository;
        this.ingredientRepository = ingredientRepository;
        this.unitService = unitService;
        this.recipeService = recipeService;
    }

    @Override
    public List<IngredientQuantity> getAllIngredientQuantity() {
        return (List<IngredientQuantity>) ingredientQuantityRepository.findAll();
    }

    @Override
    public IngredientQuantity getIngredientQuantityById(Integer ingredientQuantityId) throws IngredientQuantityNotFoundException {
        return ingredientQuantityRepository.findById(ingredientQuantityId).orElseThrow(IngredientQuantityNotFoundException::new);
    }

    @Override
    public List<IngredientQuantity> getIngredientQuantityByIngredientId(Integer ingredientId) throws IngredientNotFoundException {
        Ingredient ingredient = ingredientRepository.findById(ingredientId).orElseThrow(IngredientNotFoundException::new);
        return ingredientQuantityRepository.findByIngredient(ingredient);
    }

    @Override
    public IngredientQuantity getIngredientQuantityByIngredientAndQuantity(Ingredient ingredient, Double quantity) throws IngredientQuantityNotFoundException {
        return ingredientQuantityRepository.findByIngredientAndQuantity(ingredient, quantity).orElseThrow(IngredientQuantityNotFoundException::new);
    }

    @Override
    public IngredientQuantity saveOrUpdateIngredientQuantity(IngredientQuantityVo ingredientQuantityVo) throws IngredientNotFoundException, RecipeNotFoundException {
        validateIngredientQuantityData(ingredientQuantityVo);
        Ingredient ingredient = ingredientRepository.findById(ingredientQuantityVo.getIngredientId()).orElseThrow(IngredientNotFoundException::new);
        Unit unit = unitService.getUnitById(ingredientQuantityVo.getUnitId());
        Recipe recipe = recipeService.getRecipeById(ingredientQuantityVo.getRecipeId());
        return ingredientQuantityRepository.save(ingredientQuantityVo.toModel(recipe,ingredient,unit));
    }
}
