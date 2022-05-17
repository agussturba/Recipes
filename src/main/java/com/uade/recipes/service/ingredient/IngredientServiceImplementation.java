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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<Ingredient> getIngredientsByTypeId(Integer ingredientTypeId) {
        Type type = typeService.getTypeById(ingredientTypeId);
        return ingredientRepository.findByType(type);

    }

    @Override
    public Ingredient saveOrUpdateIngredient(IngredientVo ingredientVo) throws IngredientNameContainsNumberException {
        validateIngredientData(ingredientVo);
        if (ingredientVo.getId() == null) {
            ingredientExists(ingredientVo);
        }
        List<Type> typeList = getListType(ingredientVo.getTypeIdList());
        return ingredientRepository.save(ingredientVo.toModel(typeList));
    }

    private void ingredientExists(IngredientVo ingredientVo) {
        try {
            this.getIngredientByName(ingredientVo.getName());
            throw new IngredientNameExistsException();
        } catch (IngredientNotFoundException ignored) {}
    }
    private List<Type> getListType(List<Integer> typeIdList){
        return typeIdList.stream().map(typeId -> typeService.getTypeById(typeId)).collect(Collectors.toList());
    }

}
