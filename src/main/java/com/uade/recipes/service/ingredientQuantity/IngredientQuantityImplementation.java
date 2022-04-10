package com.uade.recipes.service.ingredientQuantity;

import com.uade.recipes.exceptions.ingredientExceptions.IngredientNotFoundException;
import com.uade.recipes.exceptions.ingredientQuantityExceptions.IngredientQuantityNotFoundException;
import com.uade.recipes.model.Ingredient;
import com.uade.recipes.model.IngredientQuantity;
import com.uade.recipes.persistance.IngredientQuantityRepository;
import com.uade.recipes.persistance.IngredientRepository;
import com.uade.recipes.vo.IngredientQuantityVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientQuantityImplementation implements IngredientQuantityService {

    private final IngredientQuantityRepository ingredientQuantityRepository;
    private final IngredientRepository ingredientRepository;

    public IngredientQuantityImplementation(IngredientQuantityRepository ingredientQuantityRepository, IngredientRepository ingredientRepository) {
        this.ingredientQuantityRepository = ingredientQuantityRepository;
        this.ingredientRepository = ingredientRepository;
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
    public IngredientQuantity saveOrUpdateIngredientQuantity(IngredientQuantityVo ingredientQuantityVo) throws IngredientNotFoundException, IngredientQuantityNotFoundException {
        Ingredient ingredient = ingredientRepository.findById(ingredientQuantityVo.getIngredientId()).orElseThrow(IngredientNotFoundException::new);
        Double quantity = ingredientQuantityVo.getQuantity();
        IngredientQuantity ingredientQuantity = new IngredientQuantity(ingredient, quantity);
        return ingredientQuantityRepository.save(ingredientQuantity);
    }
}
