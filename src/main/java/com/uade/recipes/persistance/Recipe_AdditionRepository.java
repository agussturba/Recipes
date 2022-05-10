package com.uade.recipes.persistance;

import com.uade.recipes.model.Recipe;
import com.uade.recipes.model.Recipe_Addition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Recipe_AdditionRepository extends JpaRepository<Recipe_Addition,Integer> {
    Recipe_Addition findByRecipe(Recipe recipe);
}
