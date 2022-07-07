package com.uade.recipes.service.recipeRating;

import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.exceptions.recipeRatingExceptions.RecipeRatingNotFoundException;
import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;
import com.uade.recipes.model.Recipe;
import com.uade.recipes.model.RecipeRating;
import com.uade.recipes.model.User;
import com.uade.recipes.persistance.RecipeRatingRepository;
import com.uade.recipes.service.recipe.RecipeService;
import com.uade.recipes.service.user.UserService;
import com.uade.recipes.vo.RecipeRatingVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeRatingImplementation implements RecipeRatingService {
    private final RecipeRatingRepository recipeRatingRepository;
    private final UserService userService;
    private final RecipeService recipeService;

    public RecipeRatingImplementation(RecipeRatingRepository recipeRatingRepository, UserService userService, RecipeService recipeService) {
        this.recipeRatingRepository = recipeRatingRepository;
        this.userService = userService;
        this.recipeService = recipeService;
    }

    @Override
    public List<RecipeRating> getRecipeRatingByRecipeId(Integer recipeId) throws RecipeNotFoundException {
        Recipe recipe = recipeService.getRecipeById(recipeId);
        return recipeRatingRepository.findByRecipe(recipe);
    }

    @Override
    public RecipeRating getRecipeRatingByRecipeIdAndUserId(Integer recipeId, Integer userId) throws RecipeNotFoundException, UserNotFoundException {
        Recipe recipe = recipeService.getRecipeById(recipeId);
        User user = userService.getUserById(userId);
        return recipeRatingRepository.findByRecipeAndUser(recipe, user).orElseThrow(RecipeRatingNotFoundException::new);

    }

    @Override
    public Integer getAmountOfRatingsByRecipeId(Integer recipeId) throws RecipeNotFoundException {
        Recipe recipe = recipeService.getRecipeById(recipeId);
        return recipeRatingRepository.countRecipeRatingByRecipe(recipe);
    }

    @Override
    public Double getAverageOfRecipeRatingsByRecipeId(Integer recipeId) throws RecipeNotFoundException {
        List<RecipeRating> recipeRatingList = getRecipeRatingByRecipeId(recipeId);
        Double totalRating = getTotalRating(recipeRatingList);
        Integer amountOfRatings = getAmountOfRatingsByRecipeId(recipeId);
        if (getAmountOfRatingsByRecipeId(recipeId) == 0) {
            return 0D;
        }
        return totalRating / amountOfRatings;
    }

    @Override
    public RecipeRating updateRecipeRating(RecipeRatingVo recipeRatingVo) throws UserNotFoundException, RecipeNotFoundException {
        RecipeRating recipeRating = getRecipeRatingByRecipeIdAndUserId(recipeRatingVo.getRecipeId(), recipeRatingVo.getUserId());
        recipeRating.setRating(recipeRatingVo.getRating());
        recipeRating.setComments(recipeRatingVo.getComments());
        return recipeRatingRepository.save(recipeRating);
    }

    @Override
    public RecipeRating saveRecipeRating(RecipeRatingVo recipeRatingVo) throws UserNotFoundException, RecipeNotFoundException {

        User user = userService.getUserById(recipeRatingVo.getUserId());
        Recipe recipe = recipeService.getRecipeById(recipeRatingVo.getRecipeId());
        RecipeRating recipeRating = new RecipeRating();
        recipeRating.setRating(recipeRatingVo.getRating());
        recipeRating.setComments(recipeRatingVo.getComments());
        recipeRating.setRecipe(recipe);
        recipeRating.setUser(user);
        return recipeRatingRepository.save(recipeRating);
    }

    @Override
    public Double getAverageOfRecipeRatingsByUser(Integer userId) throws UserNotFoundException, RecipeNotFoundException {
        List<Recipe> recipes = recipeService.getRecipesByOwnerId(userId);
        Double total = 0d;
        for (Recipe r : recipes) {
            total += getAverageOfRecipeRatingsByRecipeId(r.getId());
        }
        return total / recipes.size();
    }

    private Double getTotalRating(List<RecipeRating> ratingList) {
        return ratingList.stream().collect(Collectors.summingDouble(RecipeRating::getRating));
    }


}
