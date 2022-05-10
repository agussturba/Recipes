package com.uade.recipes.persistance;

import com.uade.recipes.model.FavoriteRecipe;
import com.uade.recipes.model.Recipe;
import com.uade.recipes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRecipeRepository extends JpaRepository<FavoriteRecipe, Integer> {
    List<FavoriteRecipe> findByUser(User user);

    FavoriteRecipe findByUserAndRecipe(User user, Recipe recipe);
}
