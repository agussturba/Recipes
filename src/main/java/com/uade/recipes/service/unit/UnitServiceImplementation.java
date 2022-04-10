package com.uade.recipes.service.unit;

import com.uade.recipes.exceptions.unitExceptions.UnitNotFoundException;
import com.uade.recipes.model.Unit;
import com.uade.recipes.persistance.UnitRepository;
import com.uade.recipes.vo.UnitVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitServiceImplementation implements UnitService{
    private final UnitRepository unitRepository;

    public UnitServiceImplementation(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    @Override
    public List<Unit> getAllUnits() {
        return (List<Unit>) unitRepository.findAll();
    }

    @Override
    public Unit getUnitByDescription(String description) {
        return unitRepository.findByDescription(description);
    }

    @Override
    public Unit getUnitById(Integer idUnit) {
        return unitRepository.findById(idUnit).orElseThrow(UnitNotFoundException::new);
    }

    @Override
    public Unit saveOrUpdateUnit(UnitVo unitVo) {
        return unitRepository.save(unitVo.toModel());
    }
}
