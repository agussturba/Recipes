package com.uade.recipes.service.ingredient;

import com.uade.recipes.exceptions.ingredientExceptions.IngredientNotFoundException;
import com.uade.recipes.model.Ingredient;
import com.uade.recipes.persistance.IngredientRepository;
import com.uade.recipes.vo.IngredientVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientServiceImplementation implements IngredientService {
    private final IngredientRepository ingredientRepository;

    public IngredientServiceImplementation(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    @Override
    public Ingredient getIngredientById(Integer ingredientId) {
        return ingredientRepository.findById(ingredientId).orElseThrow(IngredientNotFoundException::new);
    }

    @Override
    public Ingredient getIngredientByName(String ingredientName) {
        return ingredientRepository.findByName(ingredientName).orElseThrow(IngredientNotFoundException::new);
    }

    @Override
    public List<Ingredient> getIngredientsByType(String ingredientType) {
        return ingredientRepository.findByType(ingredientType);
    }

    @Override
    public Ingredient saveOrUpdateIngredient(IngredientVo ingredientVo) {
        if (ingredientVo.getId() != null){
            this.getIngredientById(ingredientVo.getId());
        }
        Ingredient ingredient = new Ingredient(ingredientVo.getName(), ingredientVo.getType());
        return ingredientRepository.save(ingredient);
    }


}
