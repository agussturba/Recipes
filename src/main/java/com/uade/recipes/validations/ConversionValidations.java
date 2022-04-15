package com.uade.recipes.validations;

import com.uade.recipes.exceptions.conversionExceptions.ConversionFactorLessThanZeroException;
import com.uade.recipes.exceptions.conversionExceptions.SourceUnitIdLessOrEqualToZeroException;
import com.uade.recipes.exceptions.conversionExceptions.TargetUnitIdLessOrEqualToZeroException;
import com.uade.recipes.vo.ConversionVo;

public class ConversionValidations {
    public static void validateConversionData(ConversionVo conversionVo) {
        if (conversionVo.getConversionFactor() < 0) {
            throw new ConversionFactorLessThanZeroException();
        }
        if (conversionVo.getSourceUnitId() <= 0) {
            throw new SourceUnitIdLessOrEqualToZeroException();
        }
        if (conversionVo.getTargetUnitId() <= 0) {
            throw new TargetUnitIdLessOrEqualToZeroException();
        }

    }
}
