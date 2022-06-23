package com.uade.recipes.service.recipeRating;

import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.exceptions.recipeRatingExceptions.RatingIsLowerThanZeroException;
import com.uade.recipes.exceptions.recipeRatingExceptions.RatingIsNullException;
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

import static com.uade.recipes.validations.RatingValidations.validateRatingData;

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
        System.out.println(recipe);
        User user = userService.getUserById(userId);
        System.out.println(user);
        RecipeRating recipeRating = recipeRatingRepository.findByRecipeAndUser(recipe, user);
        System.out.println(recipeRating);
        return recipeRating;
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
        return totalRating / amountOfRatings;
    }

    @Override
    public RecipeRating saveOrUpdateRecipeRating(RecipeRatingVo recipeRatingVo) throws UserNotFoundException, RecipeNotFoundException, RatingIsLowerThanZeroException, RatingIsNullException {
        validateRatingData(recipeRatingVo);

        RecipeRating recipeRating = getRecipeRatingByRecipeIdAndUserId(recipeRatingVo.getRecipeId(), recipeRatingVo.getUserId());

        if (recipeRating == null) {
            User user = userService.getUserById(recipeRatingVo.getUserId());
            Recipe recipe = recipeService.getRecipeById(recipeRatingVo.getRecipeId());
            recipeRating = recipeRatingVo.toModel(recipe, user);
        } else {
            recipeRating.setRating(recipeRatingVo.getRating());
            recipeRating.setComments(recipeRatingVo.getComments());
        }

        return recipeRatingRepository.save(recipeRating);
    }

    @Override
    public Double getAverageOfRecipeRatingsByUser(Integer userId) throws UserNotFoundException, RecipeNotFoundException {
        List<Recipe> recipes = recipeService.getRecipesByOwnerId(userId);
        Double total = 0d;
        for (Recipe r: recipes) {
            total += getAverageOfRecipeRatingsByRecipeId(r.getId());
        }
        return total / recipes.size();
    }

    private Double getTotalRating(List<RecipeRating> ratingList){
        return ratingList.stream().collect(Collectors.summingDouble(RecipeRating::getRating));
    }


}
