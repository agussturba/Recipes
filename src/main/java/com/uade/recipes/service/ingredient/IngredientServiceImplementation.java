package com.uade.recipes.service.ingredient;

import com.uade.recipes.exceptions.ingredientExceptions.IngredientNameContainsNumberException;
import com.uade.recipes.exceptions.ingredientExceptions.IngredientNotFoundException;
import com.uade.recipes.model.Ingredient;
import com.uade.recipes.persistance.IngredientRepository;
import com.uade.recipes.utilities.SaveDataDB;
import com.uade.recipes.vo.IngredientVo;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.uade.recipes.validations.IngredientsValidations.validateIngredientData;

@Service
public class IngredientServiceImplementation implements IngredientService {
    private final IngredientRepository ingredientRepository;

    public IngredientServiceImplementation(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
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
    public void saveAllIngredients(List<IngredientVo> ingredientVoList) {
        List<Ingredient> ingredients = ingredientVoList.stream().map(IngredientVo::toModel).collect(Collectors.toList());
        ingredients.stream().map(ingredient -> ingredientRepository.save(ingredient));
    }
    @Override
    public Ingredient saveIngredient(IngredientVo ingredientVo) throws IngredientNameContainsNumberException {
        validateIngredientData(ingredientVo);
        return ingredientRepository.save(ingredientVo.toModel());
    }

}
