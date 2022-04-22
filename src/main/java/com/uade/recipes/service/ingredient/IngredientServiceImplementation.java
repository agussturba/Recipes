package com.uade.recipes.service.ingredient;

import com.uade.recipes.exceptions.ingredientExceptions.IngredientNameContainsNumberException;
import com.uade.recipes.exceptions.ingredientExceptions.IngredientNameExistsException;
import com.uade.recipes.exceptions.ingredientExceptions.IngredientNotFoundException;
import com.uade.recipes.model.Ingredient;
import com.uade.recipes.model.Ingredient_Addition;
import com.uade.recipes.model.Type;
import com.uade.recipes.persistance.IngredientRepository;
import com.uade.recipes.persistance.Ingredient_AdditionRepository;
import com.uade.recipes.service.type.TypeService;
import com.uade.recipes.vo.IngredientVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.uade.recipes.validations.IngredientsValidations.validateIngredientData;

@Service
public class IngredientServiceImplementation implements IngredientService {
    private final IngredientRepository ingredientRepository;
    private final TypeService typeService;
    private final Ingredient_AdditionRepository additionRepository;

    public IngredientServiceImplementation(IngredientRepository ingredientRepository, TypeService typeService, Ingredient_AdditionRepository additionRepository) {
        this.ingredientRepository = ingredientRepository;
        this.typeService = typeService;
        this.additionRepository = additionRepository;
    }

    @Override
    public List<Ingredient> getAllIngredients() {
        return (List<Ingredient>) ingredientRepository.findAll();
    }

    @Override
    public Ingredient_Addition getIngredientById(Integer ingredientId) throws IngredientNotFoundException {
        Ingredient ingredient = ingredientRepository.findById(ingredientId).orElseThrow(IngredientNotFoundException::new);
        return additionRepository.findByIngredient(ingredient);

    }

    @Override
    public Ingredient_Addition getIngredientByName(String ingredientName) throws IngredientNotFoundException {
        Ingredient ingredient = ingredientRepository.findByName(ingredientName).orElseThrow(IngredientNotFoundException::new);
        return additionRepository.findByIngredient(ingredient);

    }

    @Override
    public List<Ingredient> getIngredientsByTypeId(Integer ingredientTypeId) {
        Type type = typeService.getTypeById(ingredientTypeId);
        List<Ingredient_Addition> ingredient_Addition_List = additionRepository.findByType(type);
        return ingredient_Addition_List.stream().map(Ingredient_Addition::getIngredient).collect(Collectors.toList());

    }

    @Override
    public Ingredient saveOrUpdateIngredient(IngredientVo ingredientVo, boolean dividable, Integer typeId) throws IngredientNameContainsNumberException {
        validateIngredientData(ingredientVo);
        if (ingredientVo.getId() == null) {
            ingredientExists(ingredientVo);
        }
        Type type = typeService.getTypeById(typeId);
        Ingredient ingredient = ingredientRepository.save(ingredientVo.toModel());
        Ingredient_Addition ingredient_addition = new Ingredient_Addition(ingredient, type, dividable);
        additionRepository.save(ingredient_addition);
        return ingredient;
    }

    private void ingredientExists(IngredientVo ingredientVo) {
        try {
            this.getIngredientByName(ingredientVo.getName());
            throw new IngredientNameExistsException();
        } catch (IngredientNotFoundException ignored) {
        }
    }

}
