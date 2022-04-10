package com.uade.recipes.persistance;

import com.uade.recipes.model.Unit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface UnitRepository extends CrudRepository<Unit,Integer> {
    Unit findByDescription(String description);
}
