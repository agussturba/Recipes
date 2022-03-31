package com.uade.recipes.persistance;

import com.uade.recipes.model.Recipe;
import com.uade.recipes.model.RecipeRating;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRatingRepository extends CrudRepository<RecipeRating,Integer> {
    RecipeRating findByRecipe(Recipe recipe);
}
