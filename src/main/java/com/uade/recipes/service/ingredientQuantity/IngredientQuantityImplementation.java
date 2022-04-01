package com.uade.recipes.service.ingredientQuantity;

import com.uade.recipes.exceptions.IngredientQuantityNotFoundException;
import com.uade.recipes.exceptions.ingredientExceptions.IngredientNotFoundException;
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
    public IngredientQuantity getIngredientQuantityById(Integer ingredientQuantityId) {
        return ingredientQuantityRepository.findById(ingredientQuantityId).orElseThrow(IngredientQuantityNotFoundException::new);
    }

    @Override
    public List<IngredientQuantity> getIngredientQuantityByIngredientId(Integer ingredientId) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId).orElseThrow(IngredientNotFoundException::new);
        return ingredientQuantityRepository.findByIngredient(ingredient);
    }

    @Override
    public IngredientQuantity getIngredientQuantityByIngredientAndQuantity(Ingredient ingredient, Double quantity) {
        return ingredientQuantityRepository.findByIngredientAndQuantity(ingredient,quantity).orElseThrow(IngredientQuantityNotFoundException::new);
    }

    @Override
    public IngredientQuantity saveOrUpdateIngredientQuantity(IngredientQuantityVo ingredientQuantityVo) {
        Ingredient ingredient = ingredientRepository.findById(ingredientQuantityVo.getIngredientId()).orElseThrow(IngredientNotFoundException::new);
        Double quantity = ingredientQuantityVo.getQuantity();
        try {
            this.getIngredientQuantityByIngredientAndQuantity(ingredient,quantity);
            return null;
        }catch (IngredientQuantityNotFoundException e){//Para no crear IngredientQuantity repetidos
            IngredientQuantity ingredientQuantity = new IngredientQuantity(ingredient,quantity);
            return ingredientQuantityRepository.save(ingredientQuantity);
        }
    }
}
