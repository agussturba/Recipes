package com.uade.recipes.service.favoriteRecipe;

import com.uade.recipes.exceptions.FavoriteRecipeNotFoundException;
import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;
import com.uade.recipes.model.FavoriteRecipe;
import com.uade.recipes.model.Recipe;
import com.uade.recipes.model.User;
import com.uade.recipes.persistance.FavoriteRecipeRepository;
import com.uade.recipes.service.recipe.RecipeService;
import com.uade.recipes.service.user.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteRecipeServiceImplementation implements FavoriteRecipeService {
    private final FavoriteRecipeRepository favoriteRecipeRepository;
    private final RecipeService recipeService;
    private final UserService userService;

    public FavoriteRecipeServiceImplementation(FavoriteRecipeRepository favoriteRecipeRepository, RecipeService recipeService, UserService userService) {
        this.favoriteRecipeRepository = favoriteRecipeRepository;
        this.recipeService = recipeService;
        this.userService = userService;
    }

    @Override
    public List<FavoriteRecipe> getAllFavoritesRecipesByUserId(Integer userId) throws UserNotFoundException {
        User user = userService.getUserById(userId);
        return favoriteRecipeRepository.findByUser(user);
    }

    @Override
    public FavoriteRecipe saveFavoriteRecipe(Integer recipeId, Integer userId) throws  RecipeNotFoundException, UserNotFoundException {
        Recipe recipe = recipeService.getRecipeById(recipeId);
        User user = userService.getUserById(userId);
        FavoriteRecipe favoriteRecipe = new FavoriteRecipe(recipe,user);
        return favoriteRecipeRepository.save(favoriteRecipe);
    }


    @Override
    public void deleteFavoriteRecipeByUserIdAndRecipeId(Integer recipeId, Integer userId) throws RecipeNotFoundException, UserNotFoundException {
        Recipe recipe = recipeService.getRecipeById(recipeId);
        User user = userService.getUserById(userId);
        FavoriteRecipe favoriteRecipe = favoriteRecipeRepository.findByUserAndRecipe(user,recipe).orElseThrow(FavoriteRecipeNotFoundException::new);
        favoriteRecipeRepository.delete(favoriteRecipe);
    }

    @Override
    public void deleteFavoriteRecipeByFavoriteRecipeId(Integer favoriteRecipeId) {
        FavoriteRecipe favoriteRecipe= favoriteRecipeRepository.findById(favoriteRecipeId).orElseThrow(FavoriteRecipeNotFoundException::new);
        favoriteRecipeRepository.delete(favoriteRecipe);
    }

    @Override
    public boolean isInFavourites(Integer recipeId, Integer userId) throws RecipeNotFoundException, UserNotFoundException {
        return favoriteRecipeRepository.findByUserAndRecipe(userService.getUserById(userId), recipeService.getRecipeById(recipeId)).isPresent();
    }
}
