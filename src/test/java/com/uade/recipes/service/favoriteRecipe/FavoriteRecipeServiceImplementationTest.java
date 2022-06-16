package com.uade.recipes.service.favoriteRecipe;

import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;
import com.uade.recipes.model.*;
import com.uade.recipes.persistance.FavoriteRecipeRepository;
import com.uade.recipes.service.recipe.RecipeService;
import com.uade.recipes.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
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
        testRecipe.setName("PAPAS");
        testRecipe.setDuration(2);
        testRecipe.setPortions(3D);
        testRecipe.setPeopleAmount(2);
        testRecipe.setType(testType);
        testRecipe.setOwner(testUser);

        favoriteRecipeTestList = new ArrayList<>();
        testFavoriteRecipe = new FavoriteRecipe();
        testFavoriteRecipe.setId(1);
        testFavoriteRecipe.setRecipe(testRecipe);
        testFavoriteRecipe.setUser(testUser);
        favoriteRecipeTestList.add(testFavoriteRecipe);

    }

    @Test
    void getAllFavoritesRecipesByUserId() throws UserNotFoundException {
        when(userService.getUserById(any(Integer.class))).thenReturn(testUser);
        when(favoriteRecipeRepository.findByUser(any(User.class))).thenReturn(favoriteRecipeTestList);
        List<FavoriteRecipe> favoriteRecipes = favoriteRecipeServiceImplementation.getAllFavoritesRecipesByUserId(1);
        assertNotNull(favoriteRecipes);
        assertEquals("Receta de Papa", favoriteRecipes.get(0).getRecipe().getDescription());
        verify(favoriteRecipeRepository).findByUser(testUser);
    }

    @Test
    void saveFavoriteRecipe() throws UserNotFoundException, RecipeNotFoundException {
        when(userService.getUserById(any(Integer.class))).thenReturn(testUser);
        when(recipeService.getRecipeById(any(Integer.class))).thenReturn(testRecipe);
        when(favoriteRecipeRepository.save(any(FavoriteRecipe.class))).thenReturn(testFavoriteRecipe);
        FavoriteRecipe favoriteRecipe = favoriteRecipeServiceImplementation.saveFavoriteRecipe(1, 1);
        assertNotNull(favoriteRecipe);
        assertEquals("Receta de Papa", favoriteRecipe.getRecipe().getDescription());
    }

    @Test
    void deleteFavoriteRecipeByUserIdAndRecipeId() throws UserNotFoundException, RecipeNotFoundException {
        when(userService.getUserById(any(Integer.class))).thenReturn(testUser);
        when(recipeService.getRecipeById(any(Integer.class))).thenReturn(testRecipe);
        when(favoriteRecipeRepository.findByUserAndRecipe(any(User.class),any(Recipe.class))).thenReturn(java.util.Optional.ofNullable(testFavoriteRecipe));
        favoriteRecipeServiceImplementation.deleteFavoriteRecipeByUserIdAndRecipeId(1,1);
        verify(favoriteRecipeRepository).delete(testFavoriteRecipe);
    }

    @Test
    void deleteFavoriteRecipeByFavoriteRecipeId() {
        when(favoriteRecipeRepository.findById(any(Integer.class))).thenReturn(java.util.Optional.ofNullable(testFavoriteRecipe));
        favoriteRecipeServiceImplementation.deleteFavoriteRecipeByFavoriteRecipeId(1);
        verify(favoriteRecipeRepository).delete(testFavoriteRecipe);
    }
}