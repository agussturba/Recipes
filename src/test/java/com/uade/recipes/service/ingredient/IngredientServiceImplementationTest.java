package com.uade.recipes.service.ingredient;

import com.uade.recipes.exceptions.ingredientExceptions.IngredientNotFoundException;
import com.uade.recipes.model.Ingredient;
import com.uade.recipes.model.Type;
import com.uade.recipes.persistance.IngredientRepository;
import com.uade.recipes.service.type.TypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplementationTest {
    @Mock
    private IngredientRepository ingredientRepository;
    @Mock
    private TypeService typeService;
    @InjectMocks
    private IngredientServiceImplementation ingredientServiceImplementation;

    private Ingredient testIngredient;
    private Type testType;
    private List<Type> typeTestList;
    private List<Ingredient> ingredientTestList;

    @BeforeEach
    void setUp() {
        typeTestList = new ArrayList<>();
        testType = new Type();
        testType.setDescription("Vegetal");
        typeTestList.add(testType);

        ingredientTestList = new ArrayList<>();

        testIngredient = new Ingredient();
        testIngredient.setName("Tomate");
        testIngredient.setId(1);
        ingredientTestList.add(testIngredient);
    }


    @Test
    void getAllIngredients() {
        when(ingredientRepository.findAll()).thenReturn(ingredientTestList);
        List<Ingredient> ingredients = ingredientServiceImplementation.getAllIngredients();
        assertNotNull(ingredients);
        assertEquals(1, ingredients.size());
        verify(ingredientRepository).findAll();

    }

    @Test
    void getIngredientById() throws IngredientNotFoundException {
        when(ingredientRepository.findById(any(Integer.class))).thenReturn(java.util.Optional.ofNullable(testIngredient));
        Ingredient ingredient = ingredientServiceImplementation.getIngredientById(1);
        assertNotNull(ingredient);
        assertEquals("Tomate", ingredient.getName());
        verify(ingredientRepository).findById(1);
    }

    @Test
    void getIngredientByName() throws IngredientNotFoundException {
        when(ingredientRepository.findByName(any(String.class))).thenReturn(java.util.Optional.ofNullable(testIngredient));
        Ingredient ingredient = ingredientServiceImplementation.getIngredientByName("Tomate");
        assertNotNull(ingredient);
        assertEquals("Tomate", ingredient.getName());
        verify(ingredientRepository).findByName("Tomate");
    }


    @Test
    void saveOrUpdateIngredient() {
        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(testIngredient);
        Ingredient ingredient = ingredientRepository.save(testIngredient);
        assertNotNull(ingredient);
        assertEquals("Tomate", ingredient.getName());
        verify(ingredientRepository).save(testIngredient);
    }
}