package com.uade.recipes.service.conversion;

import com.uade.recipes.model.Conversion;
import com.uade.recipes.vo.ConversionVo;

import java.util.List;

public interface ConversionService {
    List<Conversion> getAllConversions();

    List<Conversion> getConversionsBySourceUnitId(Integer sourceUnitId);

    List<Conversion> getConversionsByTargetUnitId(Integer targetUnitId);

    List<Conversion> getConversionsBySourceUnitIdAndTargetUnitId(Integer sourceUnitId, Integer targetUnitId);

    Conversion getConversionById(Integer conversionId);

    Conversion saveOrUpdateConversion(ConversionVo conversionVo);

}
