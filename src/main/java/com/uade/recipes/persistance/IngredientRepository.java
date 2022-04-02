package com.uade.recipes.persistance;

import com.uade.recipes.model.Ingredient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientRepository extends MongoRepository<Ingredient,Integer> {
    Optional<Ingredient> findByName(String name);
    List<Ingredient> findByType(String type);
}
