package com.uade.recipes.service.conversion;

import com.uade.recipes.exceptions.conversionExceptions.ConversionExistsException;
import com.uade.recipes.exceptions.conversionExceptions.ConversionNotFoundException;
import com.uade.recipes.model.Conversion;
import com.uade.recipes.model.Unit;
import com.uade.recipes.persistance.ConversionsRepository;
import com.uade.recipes.service.unit.UnitService;
import com.uade.recipes.vo.ConversionVo;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.uade.recipes.validations.ConversionValidations.validateConversionData;

@Service
public class ConversionServiceImplementation implements ConversionService {
    private final ConversionsRepository conversionsRepository;
    private final UnitService unitService;

    public ConversionServiceImplementation(ConversionsRepository conversionsRepository, UnitService unitService) {
        this.conversionsRepository = conversionsRepository;
        this.unitService = unitService;
    }

    @Override
    public List<Conversion> getAllConversions() {
        return (List<Conversion>) conversionsRepository.findAll();
    }

    @Override
    public List<Conversion> getConversionsBySourceUnitId(Integer sourceUnitId) {
        Unit sourceUnit = unitService.getUnitById(sourceUnitId);
        return conversionsRepository.findBySourceUnit(sourceUnit);
    }

    @Override
    public List<Conversion> getConversionsByTargetUnitId(Integer targetUnitId) {
        Unit targetUnit = unitService.getUnitById(targetUnitId);
        return conversionsRepository.findByTargetUnit(targetUnit);
    }

    @Override
    public Conversion getConversionBySourceUnitIdAndTargetUnitId(Integer sourceUnitId, Integer targetUnitId) {
        Unit targetUnit = unitService.getUnitById(targetUnitId);
        Unit sourceUnit = unitService.getUnitById(sourceUnitId);
        return conversionsRepository.findByTargetUnitAndSourceUnit(targetUnit, sourceUnit).orElseThrow(ConversionNotFoundException::new);
    }

    @Override
    public Conversion getConversionById(Integer conversionId) {
        return conversionsRepository.findById(conversionId).orElseThrow(ConversionNotFoundException::new);
    }

    @Override
    public Conversion saveOrUpdateConversion(ConversionVo conversionVo) {
        if (conversionVo.getId() == null) {
            conversionExists(conversionVo);
        }
        validateConversionData(conversionVo);
        Unit targetUnit = unitService.getUnitById(conversionVo.getTargetUnitId());
        Unit sourceUnit = unitService.getUnitById(conversionVo.getSourceUnitId());
        return conversionsRepository.save(conversionVo.toModel(sourceUnit, targetUnit));
    }

    private void conversionExists(ConversionVo conversionVo) {
        try {
           this.getConversionBySourceUnitIdAndTargetUnitId(conversionVo.getSourceUnitId(),conversionVo.getTargetUnitId());
           throw new ConversionExistsException();
        } catch (ConversionNotFoundException ignored) {

        }
    }
}
