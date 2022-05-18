package com.uade.recipes.service.ingredient;

import com.uade.recipes.exceptions.ingredientExceptions.IngredientNameContainsNumberException;
import com.uade.recipes.exceptions.ingredientExceptions.IngredientNameExistsException;
import com.uade.recipes.exceptions.ingredientExceptions.IngredientNotFoundException;
import com.uade.recipes.model.Ingredient;
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
    public Ingredient saveOrUpdateIngredient(IngredientVo ingredientVo) throws IngredientNameContainsNumberException {
        validateIngredientData(ingredientVo);
        if (ingredientVo.getId() == null) {
            ingredientExists(ingredientVo);
        }
        return ingredientRepository.save(ingredientVo.toModel());
    }

    private void ingredientExists(IngredientVo ingredientVo) {
        try {
            this.getIngredientByName(ingredientVo.getName());
            throw new IngredientNameExistsException();
        } catch (IngredientNotFoundException ignored) {}
    }


}
