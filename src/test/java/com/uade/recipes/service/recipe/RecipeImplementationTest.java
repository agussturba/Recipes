package com.uade.recipes.service.recipe;

import com.uade.recipes.model.*;
import com.uade.recipes.persistance.RecipeRepository;
import com.uade.recipes.service.dish.DishService;
import com.uade.recipes.service.ingredientQuantity.IngredientQuantityService;
import com.uade.recipes.service.type.TypeService;
import com.uade.recipes.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


class RecipeImplementationTest {
    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private UserService userService;
    @Mock
    private DishService dishService;
    @Mock
    private TypeService typeService;
    @Mock
    private IngredientQuantityService ingredientQuantityService;

    @InjectMocks
    private RecipeImplementation recipeImplementation;

    private Recipe testRecipe;
    private User testUser;
    private Type testType;
    private RecipePhoto testRecipePhoto;
    private Ingredient testIngredient;
    private IngredientQuantity testIngredientQuantity;
    private Unit testUnit;
    private Dish testDish;
    private List<Type> typeTestList;
    private List<Recipe> recipeTestList;


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

        testDish = new Dish("Pizza", testType);

        recipeTestList.add(testRecipe);

    }

    @Test
    void getAllRecipes() {
    }

    @Test
    void getRecipeById() {
    }

    @Test
    void getRecipesByName() {
    }

    @Test
    void getRecipesByPeopleAmount() {
    }

    @Test
    void getRecipesByUserId() {
    }

    @Test
    void getRecipesByTypes() {
    }

    @Test
    void getRecipesByMissingIngredientId() {
    }

    @Test
    void getRecipesByUserIdAndPeopleAmount() {
    }

    @Test
    void getRecipesByUserIdAndDishIdAndPeopleAmount() {
    }

    @Test
    void getRecipesByDishIdAndPeopleAmount() {
    }

    @Test
    void getRecipesByUserIdAndDishId() {
    }

    @Test
    void getRecipesByDishId() {
    }

    @Test
    void isRecipeEnabled() {
    }

    @Test
    void saveOrUpdateRecipe() {
    }

    @Test
    void convertRecipeIngredientQuantityByIngredientIdAndRecipeIdAndNewQuantity() {
    }

    @Test
    void convertRecipeIngredientQuantityByConversionFactor() {
    }
}