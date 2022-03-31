package com.uade.recipes.persistance;

import com.uade.recipes.model.Dish;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DishRepository extends MongoRepository<Dish, Integer> {
    Optional<Dish> findByName(String name);

    List<Dish> findByType(String type);
}
