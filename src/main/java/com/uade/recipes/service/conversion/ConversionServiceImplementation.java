package com.uade.recipes.service.conversion;

import com.uade.recipes.exceptions.conversionExceptions.ConversionNotFoundException;
import com.uade.recipes.exceptions.unitExceptions.UnitNotFoundException;
import com.uade.recipes.model.Conversion;
import com.uade.recipes.model.Unit;
import com.uade.recipes.persistance.ConversionsRepository;
import com.uade.recipes.persistance.UnitRepository;
import com.uade.recipes.vo.ConversionVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversionServiceImplementation implements ConversionService {
    private final ConversionsRepository conversionsRepository;
    private final UnitRepository unitRepository;

    public ConversionServiceImplementation(ConversionsRepository conversionsRepository, UnitRepository unitRepository) {
        this.conversionsRepository = conversionsRepository;
        this.unitRepository = unitRepository;
    }

    @Override
    public List<Conversion> getAllConversions() {
        return (List<Conversion>) conversionsRepository.findAll();
    }

    @Override
    public List<Conversion> getConversionsBySourceUnitId(Integer sourceUnitId) {
        Unit sourceUnit = unitRepository.findById(sourceUnitId).orElseThrow(UnitNotFoundException::new);
        return conversionsRepository.findBySourceUnit(sourceUnit);
    }

    @Override
    public List<Conversion> getConversionsByTargetUnitId(Integer targetUnitId) {
        Unit targetUnit = unitRepository.findById(targetUnitId).orElseThrow(UnitNotFoundException::new);
        return conversionsRepository.findByTargetUnit(targetUnit);
    }

    @Override
    public List<Conversion> getConversionsBySourceUnitIdAndTargetUnitId(Integer sourceUnitId, Integer targetUnitId) {
        Unit targetUnit = unitRepository.findById(targetUnitId).orElseThrow(UnitNotFoundException::new);
        Unit sourceUnit = unitRepository.findById(sourceUnitId).orElseThrow(UnitNotFoundException::new);
        return (List<Conversion>) conversionsRepository.findByTargetUnitAndSourceUnit(targetUnit, sourceUnit);
    }

    @Override
    public Conversion getConversionById(Integer conversionId) {
        return conversionsRepository.findById(conversionId).orElseThrow(ConversionNotFoundException::new);
    }

    @Override
    public Conversion saveOrUpdateConversion(ConversionVo conversionVo) {//TODO ADD VALIDATIONS
        Unit targetUnit = unitRepository.findById(conversionVo.getTargetUnitId()).orElseThrow(UnitNotFoundException::new);
        Unit sourceUnit = unitRepository.findById(conversionVo.getSourceUnitId()).orElseThrow(UnitNotFoundException::new);
        return conversionsRepository.save(conversionVo.toModel(sourceUnit, targetUnit));
    }
}
