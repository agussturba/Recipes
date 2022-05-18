package com.uade.recipes.persistance;

import com.uade.recipes.model.Ingredient;
import com.uade.recipes.model.IngredientQuantity;
import com.uade.recipes.model.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientQuantityRepository extends CrudRepository<IngredientQuantity, Integer> {
    List<IngredientQuantity> findByIngredient(Ingredient ingredient);

    List<IngredientQuantity> findByRecipe(Recipe recipe);

    IngredientQuantity findByRecipeAndIngredient(Recipe recipe, Ingredient ingredient);

}
