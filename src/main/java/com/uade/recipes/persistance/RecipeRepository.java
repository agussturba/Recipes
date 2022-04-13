package com.uade.recipes.persistance;

import com.uade.recipes.model.Dish;
import com.uade.recipes.model.Recipe;
import com.uade.recipes.model.Type;
import com.uade.recipes.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Integer> {
    List<Recipe> findByName(String name);

    List<Recipe> findByUser(User user);

    List<Recipe> findByDish(Dish dish);

    List<Recipe> findByUserAndDish(User user, Dish dish);

    List<Recipe> findByUserAndPeopleAmount(User user, Integer peopleAmount);

    List<Recipe> findByDishAndPeopleAmount(Dish dish, Integer peopleAmount);

    List<Recipe> findByDishAndUserAndPeopleAmount(Dish dish, User user, Integer peopleAmount);

    List<Recipe> findByUserAndTypeIsIn(User user, List<Type> types);

    List<Recipe> findByPeopleAmountAndTypeIsIn(Integer peopleAmount, List<Type> types);

    List<Recipe> findByTypeIn(List<Type> type);

    List<Recipe> findByPeopleAmount(Integer peopleAmount);
}
