package com.uade.recipes.service.unit;

import com.uade.recipes.model.Unit;
import com.uade.recipes.vo.UnitVo;

import java.util.List;

public interface UnitService {
    List<Unit> getAllUnits();

    Unit getUnitByDescription(String description);

    Unit getUnitById(Integer idUnit);

    Unit saveOrUpdateUnit(UnitVo unitVo);
}
