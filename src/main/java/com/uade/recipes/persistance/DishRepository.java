package com.uade.recipes.persistance;

import com.uade.recipes.model.Dish;
import com.uade.recipes.model.Recipe;
import com.uade.recipes.model.Type;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DishRepository extends CrudRepository<Dish, Integer> {
    Optional<Dish> findByName(String name);

    List<Dish> findByTypeIsIn(List<Type> type);

    Dish findByRecipes(Recipe recipe);
}
