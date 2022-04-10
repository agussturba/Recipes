package com.uade.recipes.persistance;

import com.uade.recipes.model.Recipe;
import com.uade.recipes.model.RecipePhoto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipePhotoRepository extends CrudRepository<RecipePhoto, Integer> {
    List<RecipePhoto> findByRecipe(Recipe recipe);
}
