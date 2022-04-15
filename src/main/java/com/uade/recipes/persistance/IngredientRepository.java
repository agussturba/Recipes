package com.uade.recipes.persistance;

import com.uade.recipes.model.Ingredient;
import com.uade.recipes.model.Type;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, Integer> {
    Optional<Ingredient> findByName(String name);

    List<Ingredient> findByType(Type type);
}
