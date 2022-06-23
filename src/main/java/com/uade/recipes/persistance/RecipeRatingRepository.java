package com.uade.recipes.persistance;

import com.uade.recipes.model.Recipe;
import com.uade.recipes.model.RecipeRating;
import com.uade.recipes.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRatingRepository extends CrudRepository<RecipeRating, Integer> {
    List<RecipeRating> findByRecipe(Recipe recipe);

    Optional<RecipeRating> findByRecipeAndUser(Recipe recipe, User user);

    Integer countRecipeRatingByRecipe(Recipe recipe);
}
