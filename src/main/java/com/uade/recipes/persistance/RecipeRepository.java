package com.uade.recipes.persistance;

import com.uade.recipes.model.Dish;
import com.uade.recipes.model.Recipe;
import com.uade.recipes.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends MongoRepository<Recipe,Integer> {
    List<Recipe> findByName(String name);
    List<Recipe> findByUser(User user);
    List<Recipe> findByDish(Dish dish);
    List<Recipe> findByUserAndDish(User user,Dish dish);
    List<Recipe> findRecipeByLabelsContains(List<String> labels);
}
