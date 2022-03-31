package com.uade.recipes.persistance;

import com.uade.recipes.model.Dish;
import com.uade.recipes.model.Recipe;
import com.uade.recipes.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe,Integer> {
    Optional<Recipe> findByName(String name);
    List<Recipe> findByType(String type);
    List<Recipe> findByUser(User user);
    List<Recipe> findByDish(Dish dish);
    List<Recipe> findByUserAndDish(User user,Dish dish);
    List<Recipe> findByUserAndType(User user,String type);
    List<Recipe> findByDishAndType(Dish dish,String type);
    List<Recipe> findByDishAndTypeAndUser(Dish dish,String type,User user);
}
