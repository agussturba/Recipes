package com.uade.recipes.persistance;

import com.uade.recipes.model.Type;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends CrudRepository<Type,Integer> {
    Type findByDescription(String description);
}
