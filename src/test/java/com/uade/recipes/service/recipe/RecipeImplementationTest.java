package com.uade.recipes.service.recipe;

import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;
import com.uade.recipes.model.Ingredient;
import com.uade.recipes.model.IngredientQuantity;
import com.uade.recipes.model.Recipe;
import com.uade.recipes.model.RecipePhoto;
import com.uade.recipes.model.Type;
import com.uade.recipes.model.Unit;
import com.uade.recipes.model.User;
import com.uade.recipes.persistance.RecipeRepository;
import com.uade.recipes.service.ingredientQuantity.IngredientQuantityService;
import com.uade.recipes.service.type.TypeService;
import com.uade.recipes.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecipeImplementationTest {
    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private UserService userService;
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
    private List<Type> typeTestList;
    private List<Recipe> recipeTestList;


    @BeforeEach
    void setUp() {
        recipeTestList = new ArrayList<>();
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
        testRecipe.setName("PAPAS");
        testRecipe.setDuration(2);
        testRecipe.setPortions(3D);
        testRecipe.setPeopleAmount(1);
        testRecipe.setType(testType);
        testRecipe.setOwner(testUser);

        testUnit = new Unit();
        testUnit.setDescription("KG");

        typeTestList = new ArrayList<>();
        testType = new Type();
        testType.setDescription("Vegetal");
        typeTestList.add(testType);


        testIngredient = new Ingredient();
        testIngredient.setDividable(true);
        testIngredient.setName("Tomate");

        testIngredientQuantity = new IngredientQuantity();
        testIngredientQuantity.setIngredient(testIngredient);
        testIngredientQuantity.setQuantity(2D);
        testIngredientQuantity.setObservations("Tratar con cuidado");
        testIngredientQuantity.setRecipe(testRecipe);
        testIngredientQuantity.setUnit(testUnit);

        recipeTestList.add(testRecipe);

    }

    @Test
    void getAllRecipes() {
        when(recipeRepository.findAll()).thenReturn(recipeTestList);
        List<Recipe> recipes = recipeImplementation.getAllRecipes();
        assertNotNull(recipes);
        assertEquals(1,recipes.size());
        verify(recipeRepository).findAll();
    }

    @Test
    void getRecipeById() throws RecipeNotFoundException {
        when(recipeRepository.findById(any(Integer.class))).thenReturn(java.util.Optional.ofNullable(testRecipe));
        Recipe recipe = recipeImplementation.getRecipeById(1);
        assertNotNull(recipe);
        assertEquals("PAPAS",recipe.getName());
        verify(recipeRepository).findById(1);
    }

    @Test
    void getRecipesByName() {
        when(recipeRepository.findByNameOrderByName(any(String.class))).thenReturn(recipeTestList);
        List<Recipe> recipes = recipeImplementation.getRecipesByName("papas");
        assertNotNull(recipes);
        assertEquals(1,recipes.size());
        verify(recipeRepository).findByNameOrderByName("papas");
    }

    @Test
    void getRecipesByPeopleAmount() {
        when(recipeRepository.findByPeopleAmount(any(Integer.class))).thenReturn(recipeTestList);
        List<Recipe> recipes = recipeImplementation.getRecipesByPeopleAmount(1);
        assertNotNull(recipes);
        assertEquals(1,recipes.size());
        verify(recipeRepository).findByPeopleAmount(1);
    }

    @Test
    void getRecipesByOwnerId() throws UserNotFoundException {
        when(userService.getUserById(any(Integer.class))).thenReturn(testUser);
        when(recipeRepository.findByOwnerOrderByName(any(User.class))).thenReturn(recipeTestList);
        List<Recipe> recipes = recipeImplementation.getRecipesByOwnerId(1);
        assertNotNull(recipes);
        assertEquals(1,recipes.size());
        verify(recipeRepository).findByOwnerOrderByName(testUser);
    }

    @Test
    void getRecipesByTypes() {
        when(typeService.getTypesByIdList(any(List.class))).thenReturn(typeTestList);
        when(recipeRepository.findByTypeInOrderByName(any(List.class))).thenReturn(recipeTestList);
        List<Recipe> recipes = recipeImplementation.getRecipesByTypes(Arrays.asList(1));
        assertNotNull(recipes);
        assertEquals(1,recipes.size());
        verify(recipeRepository).findByTypeInOrderByName(typeTestList);
    }

    @Test
    void getRecipesByMissingIngredientId() {
    }

    @Test
    void getRecipesByOwnerIdAndPeopleAmount() throws UserNotFoundException {
        when(userService.getUserById(any(Integer.class))).thenReturn(testUser);
        when(recipeRepository.findByOwnerAndPeopleAmount(any(User.class),any(Integer.class))).thenReturn(recipeTestList);
        List<Recipe> recipes = recipeImplementation.getRecipesByOwnerIdAndPeopleAmount(1,1);
        assertNotNull(recipes);
        assertEquals(1,recipes.size());
        verify(recipeRepository).findByOwnerAndPeopleAmount(testUser,1);

    }




    @Test
    void isRecipeEnabled() throws RecipeNotFoundException {
        when(recipeRepository.findById(any(Integer.class))).thenReturn(java.util.Optional.ofNullable(testRecipe));
        Boolean isEnabled = recipeImplementation.isRecipeEnabled(1);
        assertTrue(isEnabled);
        verify(recipeRepository).findById(1);
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