package com.uade.recipes.service.favoriteRecipe;

import com.uade.recipes.model.*;
import com.uade.recipes.persistance.FavoriteRecipeRepository;
import com.uade.recipes.service.recipe.RecipeService;
import com.uade.recipes.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class FavoriteRecipeServiceImplementationTest {
    @Mock
    private FavoriteRecipeRepository favoriteRecipeRepository;
    @Mock
    private RecipeService recipeService;
    @Mock
    private UserService userService;
    @InjectMocks
    private FavoriteRecipeServiceImplementation favoriteRecipeServiceImplementation;

    private Recipe testRecipe;
    private User testUser;
    private Type testType;
    private RecipePhoto testRecipePhoto;
    private FavoriteRecipe testFavoriteRecipe;
    private List<FavoriteRecipe> favoriteRecipeTestList;

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
        testRecipe.setRecipePhoto(Arrays.asList(testRecipePhoto));
        testRecipe.setName("PAPAS");
        testRecipe.setDuration(2);
        testRecipe.setPortions(3D);
        testRecipe.setPeopleAmount(2);
        testRecipe.setType(testType);
        testRecipe.setUser(testUser);

        favoriteRecipeTestList = new ArrayList<>();
        testFavoriteRecipe = new FavoriteRecipe();
        testFavoriteRecipe.setId(1);
        testFavoriteRecipe.setRecipe(testRecipe);
        testFavoriteRecipe.setUser(testUser);
        favoriteRecipeTestList.add(testFavoriteRecipe);

    }

    @Test
    void getAllFavoritesRecipesByUserId() {
    }

    @Test
    void saveFavoriteRecipe() {
    }

    @Test
    void deleteFavoriteRecipeByUserIdAndRecipeId() {
    }

    @Test
    void deleteFavoriteRecipeByFavoriteRecipeId() {
    }
}