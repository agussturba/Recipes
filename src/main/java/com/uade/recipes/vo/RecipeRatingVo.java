package com.uade.recipes.vo;

import com.uade.recipes.model.Recipe;
import com.uade.recipes.model.RecipeRating;
import com.uade.recipes.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeRatingVo {
    Integer id;
    Integer recipeId;
    Integer userId;
    String comments;
    Double rating;

    public RecipeRating toModel(Recipe recipe,User user){
        RecipeRating recipeRating = new RecipeRating();
        recipeRating.setRating(rating);
        recipeRating.setRecipe(recipe);
        recipeRating.setId(id);
        recipeRating.setUser(user);
        recipeRating.setComments(comments);
        return recipeRating;
    }

}
