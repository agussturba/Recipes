package com.uade.recipes.persistance;

import com.uade.recipes.model.Recipe;
import com.uade.recipes.model.RecipeRating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRatingRepository extends MongoRepository<RecipeRating,Integer> {
    RecipeRating findByRecipe(Recipe recipe);
}
