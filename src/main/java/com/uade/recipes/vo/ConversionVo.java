package com.uade.recipes.vo;

import com.uade.recipes.model.Conversion;
import com.uade.recipes.model.Unit;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConversionVo {
    Integer id;
    Integer sourceUnitId;
    Integer targetUnitId;
    double conversionFactor;

    public Conversion toModel(Unit sourceUnit, Unit targetUnit) {
        Conversion conversion = new Conversion();
        conversion.setId(id);
        conversion.setConversionFactor(conversionFactor);
        conversion.setSourceUnit(sourceUnit);
        conversion.setTargetUnit(targetUnit);
        return conversion;
    }
}
