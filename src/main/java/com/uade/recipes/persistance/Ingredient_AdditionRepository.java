package com.uade.recipes.persistance;

import com.uade.recipes.model.Ingredient;
import com.uade.recipes.model.Ingredient_Addition;
import com.uade.recipes.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Ingredient_AdditionRepository extends JpaRepository<Ingredient_Addition,Integer> {
    List<Ingredient_Addition> findByType(Type type);
    Ingredient_Addition findByIngredient(Ingredient ingredient);
}
