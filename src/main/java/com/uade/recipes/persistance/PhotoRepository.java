package com.uade.recipes.persistance;

import com.uade.recipes.model.Photo;
import com.uade.recipes.model.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends CrudRepository<Photo,Integer> {
    List<Photo> findByRecipe(Recipe recipe);
}
