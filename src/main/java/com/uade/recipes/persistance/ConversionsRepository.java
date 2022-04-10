package com.uade.recipes.persistance;

import com.uade.recipes.model.Conversion;
import com.uade.recipes.model.Unit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversionsRepository extends CrudRepository<Conversion, Integer> {
    List<Conversion> findBySourceUnit(Unit sourceUnit);

    List<Conversion> findByTargetUnit(Unit targetUnit);

    Conversion findByTargetUnitAndSourceUnit(Unit targetUnit, Unit sourceUnit);

}