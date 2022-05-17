package com.uade.recipes.persistance;

import com.uade.recipes.model.Recipe;
import com.uade.recipes.model.Type;
import com.uade.recipes.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Integer> {
    List<Recipe> findByNameOrderByName(String name);

    List<Recipe> findByUserOrderByName(User user);

    List<Recipe> findByUserAndPeopleAmount(User user, Integer peopleAmount);

    List<Recipe> findByUserAndTypeIsIn(User user, List<Type> types);

    List<Recipe> findByPeopleAmountAndTypeIsIn(Integer peopleAmount, List<Type> types);

    List<Recipe> findByTypeInOrderByName(List<Type> type);

    List<Recipe> findByPeopleAmount(Integer peopleAmount);
}
