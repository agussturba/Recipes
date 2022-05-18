package com.uade.recipes.service.ingredientQuantity;

import com.uade.recipes.exceptions.ingredientExceptions.CannotDivideTheIngredientException;
import com.uade.recipes.exceptions.ingredientExceptions.IngredientNotFoundException;
import com.uade.recipes.exceptions.ingredientQuantityExceptions.IngredientQuantityNotFoundException;
import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.model.*;
import com.uade.recipes.persistance.IngredientQuantityRepository;
import com.uade.recipes.persistance.RecipeRepository;
import com.uade.recipes.service.conversion.ConversionService;
import com.uade.recipes.service.ingredient.IngredientService;
import com.uade.recipes.service.unit.UnitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngredientQuantityImplementationTest {
    @Mock
    private IngredientQuantityRepository ingredientQuantityRepository;
    @Mock
    private IngredientService ingredientService;
    @Mock
    private UnitService unitService;
    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private ConversionService conversionService;
    @InjectMocks
    private IngredientQuantityImplementation ingredientQuantityImplementation;

    private Ingredient testIngredient;
    private IngredientQuantity testIngredientQuantity;
    private Unit testUnit;
    private Recipe testRecipe;
    private Set<Recipe> testSetRecipes;
    private User testUser;
    private Type testType;
    private RecipePhoto testRecipePhoto;
    private List<Type> typeTestList;
    private List<IngredientQuantity> ingredientQuantityTestList;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1);
        testUser.setUserName("agussturba");
        testUser.setEnabled(true);
        testUser.setAvatar(null);
        testUser.setName("Agustin");
        testUser.setEmail("agus@mail.com");
        testUser.setPassword("12345");

        testRecipePhoto = new RecipePhoto();

        testType = new Type("Italiana");
        testType.setId(1);

        testSetRecipes = new HashSet<>();
        testRecipe = new Recipe();
        testRecipe.setTimestamp(LocalDateTime.now());
        testRecipe.setDescription("Receta de Papa");
        testRecipe.setEnabled(true);
        testRecipe.setRecipePhoto(Arrays.asList(testRecipePhoto));
        testRecipe.setName("PAPAS");
        testRecipe.setDuration(2);
        testRecipe.setPortions(3D);
        testRecipe.setPeopleAmount(2);
        testRecipe.setType(testType);
        testRecipe.setUser(testUser);
        testSetRecipes.add(testRecipe);

        testUnit = new Unit();
        testUnit.setDescription("KG");

        typeTestList = new ArrayList<>();
        testType = new Type();
        testType.setDescription("Vegetal");
        typeTestList.add(testType);

        testIngredient = new Ingredient();
        testIngredient.setId(1);
        testIngredient.setDividable(true);
        testIngredient.setName("Tomate");

        ingredientQuantityTestList = new ArrayList<>();
        testIngredientQuantity = new IngredientQuantity();
        testIngredientQuantity.setIngredient(testIngredient);
        testIngredientQuantity.setQuantity(2D);
        testIngredientQuantity.setObservations("Tratar con cuidado");
        testIngredientQuantity.setRecipe(testRecipe);
        testIngredientQuantity.setUnit(testUnit);
        ingredientQuantityTestList.add(testIngredientQuantity);


    }

    @Test
    void getAllIngredientQuantity() {
        when(ingredientQuantityRepository.findAll()).thenReturn(ingredientQuantityTestList);
        List<IngredientQuantity> ingredientQuantityList = ingredientQuantityImplementation.getAllIngredientQuantity();
        assertNotNull(ingredientQuantityList);
        assertEquals(1, ingredientQuantityList.size());
        verify(ingredientQuantityRepository).findAll();
    }

    @Test
    void getIngredientQuantityByRecipeId() throws RecipeNotFoundException {
        when(recipeRepository.findById(any(Integer.class))).thenReturn(java.util.Optional.ofNullable(testRecipe));
        when(ingredientQuantityRepository.findByRecipe(any(Recipe.class))).thenReturn(ingredientQuantityTestList);
        List<IngredientQuantity> ingredientQuantityList = ingredientQuantityImplementation.getIngredientQuantityByRecipeId(1);
        assertNotNull(ingredientQuantityList);
        assertEquals(1, ingredientQuantityList.size());
        verify(ingredientQuantityRepository).findByRecipe(testRecipe);

    }

    @Test
    void getConvertedIngredientQuantityListByRecipeIdAndConversionFactor() throws IngredientNotFoundException, RecipeNotFoundException, CannotDivideTheIngredientException {
        when(ingredientService.getIngredientById(any(Integer.class))).thenReturn(testIngredient);
        when(recipeRepository.findById(any(Integer.class))).thenReturn(java.util.Optional.ofNullable(testRecipe));
        when(ingredientQuantityRepository.findByRecipe(any(Recipe.class))).thenReturn(ingredientQuantityTestList);
        List<IngredientQuantity> ingredientQuantityList = ingredientQuantityImplementation.getConvertedIngredientQuantityListByRecipeIdAndConversionFactor(1,2D);
        assertNotNull(ingredientQuantityList);
        assertEquals(1, ingredientQuantityList.size());
        assertEquals(4D,ingredientQuantityList.get(0).getQuantity());
        verify(ingredientQuantityRepository).findByRecipe(testRecipe);
    }

    @Test
    void getIngredientQuantityById() throws IngredientQuantityNotFoundException {
        when(ingredientQuantityRepository.findById(any(Integer.class))).thenReturn(java.util.Optional.ofNullable(testIngredientQuantity));
        IngredientQuantity ingredientQuantity = ingredientQuantityImplementation.getIngredientQuantityById(1);
        assertNotNull(ingredientQuantity);
        assertEquals("Tomate", ingredientQuantity.getIngredient().getName());
        verify(ingredientQuantityRepository).findById(1);
    }

    @Test
    void getIngredientQuantityByIngredientId() throws IngredientNotFoundException {
        when(ingredientService.getIngredientById(any(Integer.class))).thenReturn(testIngredient);
        when(ingredientQuantityRepository.findByIngredient(any(Ingredient.class))).thenReturn(ingredientQuantityTestList);
        List<IngredientQuantity> ingredientQuantity = ingredientQuantityImplementation.getIngredientQuantityByIngredientId(1);
        assertNotNull(ingredientQuantity);
        assertEquals(1, ingredientQuantity.size());
        verify(ingredientQuantityRepository).findByIngredient(testIngredient);
    }

    @Test
    void getRecipesByIngredientId() throws IngredientNotFoundException {
        when(ingredientService.getIngredientById(any(Integer.class))).thenReturn(testIngredient);
        when(ingredientQuantityRepository.findByIngredient(any(Ingredient.class))).thenReturn(ingredientQuantityTestList);

    }

    @Test
    void saveOrUpdateIngredientQuantity() {
    }

    @Test
    void convertIngredientQuantityUnitByTargetUnitId() {
    }

    @Test
    void getIngredientQuantityByIngredientIdAndRecipeId() {
    }
}