package com.uade.recipes.service.ingredientQuantity;

import com.uade.recipes.model.*;
import com.uade.recipes.persistance.IngredientQuantityRepository;
import com.uade.recipes.persistance.RecipeRepository;
import com.uade.recipes.service.conversion.ConversionService;
import com.uade.recipes.service.ingredient.IngredientService;
import com.uade.recipes.service.unit.UnitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private User testUser;
    private Type testType;
    private RecipePhoto testRecipePhoto;
    private List<Type> typeTestList;

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

        testRecipe = new Recipe();
        testRecipe.setTimestamp(LocalDateTime.now());
        testRecipe.setDescription("Receta de Papa");
        testRecipe.setEnabled(true);
        testRecipe.setRecipePhoto(testRecipePhoto);
        testRecipe.setName("PAPAS");
        testRecipe.setDuration(2);
        testRecipe.setPortions(3D);
        testRecipe.setPeopleAmount(2);
        testRecipe.setType(testType);
        testRecipe.setUser(testUser);

        testUnit = new Unit();
        testUnit.setDescription("KG");

        typeTestList = new ArrayList<>();
        testType = new Type();
        testType.setDescription("Vegetal");
        typeTestList.add(testType);

        testIngredient = new Ingredient();
        testIngredient.setDividable(true);
        testIngredient.setName("Tomate");
        testIngredient.setType(typeTestList);

        testIngredientQuantity = new IngredientQuantity();
        testIngredientQuantity.setIngredient(testIngredient);
        testIngredientQuantity.setQuantity(2D);
        testIngredientQuantity.setObservations("Tratar con cuidado");
        testIngredientQuantity.setRecipe(testRecipe);
        testIngredientQuantity.setUnit(testUnit);
    }

    @Test
    void getAllIngredientQuantity() {
    }

    @Test
    void getIngredientQuantityByRecipeId() {
    }

    @Test
    void getConvertedIngredientQuantityListByRecipeIdAndConversionFactor() {
    }

    @Test
    void getIngredientQuantityById() {
    }

    @Test
    void getIngredientQuantityByIngredientId() {
    }

    @Test
    void getRecipesByIngredientId() {
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