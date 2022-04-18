package com.uade.recipes.persistance;

import com.uade.recipes.model.Unit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface UnitRepository extends CrudRepository<Unit,Integer> {
    Optional<Unit> findByDescription(String description);
}
