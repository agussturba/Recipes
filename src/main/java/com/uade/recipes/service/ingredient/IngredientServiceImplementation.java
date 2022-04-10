package com.uade.recipes.service.ingredient;

import com.uade.recipes.exceptions.ingredientExceptions.IngredientNameContainsNumberException;
import com.uade.recipes.exceptions.ingredientExceptions.IngredientNotFoundException;
import com.uade.recipes.exceptions.ingredientExceptions.IngredientTypeContainsNumberException;
import com.uade.recipes.model.Ingredient;
import com.uade.recipes.model.Type;
import com.uade.recipes.persistance.IngredientRepository;
import com.uade.recipes.service.type.TypeService;
import com.uade.recipes.vo.IngredientVo;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.uade.recipes.validations.IngredientsValidations.validateIngredientData;

@Service
public class IngredientServiceImplementation implements IngredientService {
    private final IngredientRepository ingredientRepository;
    private final TypeService typeService;

    public IngredientServiceImplementation(IngredientRepository ingredientRepository, TypeService typeService) {
        this.ingredientRepository = ingredientRepository;
        this.typeService = typeService;
    }

    @Override
    public List<Ingredient> getAllIngredients() {
        return (List<Ingredient>) ingredientRepository.findAll();
    }

    @Override
    public Ingredient getIngredientById(Integer ingredientId) throws IngredientNotFoundException {
        return ingredientRepository.findById(ingredientId).orElseThrow(IngredientNotFoundException::new);
    }

    @Override
    public Ingredient getIngredientByName(String ingredientName) throws IngredientNotFoundException {
        return ingredientRepository.findByName(ingredientName).orElseThrow(IngredientNotFoundException::new);
    }

    @Override
    public List<Ingredient> getIngredientsByType(String ingredientType) {
        return ingredientRepository.findByType(ingredientType);
    }

    @Override
    public Ingredient saveOrUpdateIngredient(IngredientVo ingredientVo) throws IngredientTypeContainsNumberException, IngredientNameContainsNumberException {
        validateIngredientData(ingredientVo);
        Type type = typeService.getTypeById(ingredientVo.getTypeId());
        return ingredientRepository.save(ingredientVo.toModel(type));
    }


}
