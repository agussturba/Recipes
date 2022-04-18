package com.uade.recipes.persistance;

import com.uade.recipes.model.Type;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypeRepository extends CrudRepository<Type,Integer> {
    Optional<Type> findByDescription(String description);
}
