package com.uade.recipes.persistance;

import com.uade.recipes.model.Recipe;
import com.uade.recipes.model.Type;
import com.uade.recipes.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Integer> {
    List<Recipe> findByNameOrderByName(String name);

    List<Recipe> findByOwnerOrderByName(User owner);

    List<Recipe> findByOwnerAndPeopleAmount(User owner, Integer peopleAmount);

    List<Recipe> findByTypeInOrderByName(List<Type> type);

    List<Recipe> findByPeopleAmount(Integer peopleAmount);

    Optional<List<Recipe>> findByNameContaining(@Param("name") String name);
}
