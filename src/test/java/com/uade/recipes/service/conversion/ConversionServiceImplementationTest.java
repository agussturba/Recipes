package com.uade.recipes.service.conversion;

import com.uade.recipes.model.Conversion;
import com.uade.recipes.model.Unit;
import com.uade.recipes.persistance.ConversionsRepository;
import com.uade.recipes.service.unit.UnitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConversionServiceImplementationTest {
    @Mock
    private ConversionsRepository conversionsRepository;
    @Mock
    private UnitService unitService;
    @InjectMocks
    private ConversionServiceImplementation conversionServiceImplementation;

    private Unit sourceTestUnit;
    private Unit targetTestUnit;

    private Conversion testConversion;
    private List<Unit> unitTestList;
    private List<Conversion> conversionTestList;

    @BeforeEach
    void setUp() {
        unitTestList = new ArrayList<>();
        sourceTestUnit = new Unit();
        targetTestUnit = new Unit();
        targetTestUnit.setId(1);
        targetTestUnit.setDescription("G");
        sourceTestUnit.setDescription("KG");
        sourceTestUnit.setId(2);
        unitTestList.add(sourceTestUnit);
        unitTestList.add(targetTestUnit);

        conversionTestList = new ArrayList<>();
        testConversion = new Conversion();
        testConversion.setSourceUnit(sourceTestUnit);
        testConversion.setTargetUnit(targetTestUnit);
        testConversion.setConversionFactor(1000D);
        conversionTestList.add(testConversion);

    }

    @Test
    void getAllConversions() {
        when(conversionsRepository.findAll()).thenReturn(conversionTestList);
        List<Conversion> result = conversionServiceImplementation.getAllConversions();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(conversionsRepository).findAll();
    }

    @Test
    void getConversionsBySourceUnitId() {
        when(conversionsRepository.findBySourceUnit(any(Unit.class))).thenReturn(conversionTestList);
        when(unitService.getUnitById(any(Integer.class))).thenReturn(sourceTestUnit);
        List<Conversion> result = conversionServiceImplementation.getConversionsBySourceUnitId(sourceTestUnit.getId());
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(unitService).getUnitById(2);
        verify(conversionsRepository).findBySourceUnit(sourceTestUnit);
    }

    @Test
    void getConversionsByTargetUnitId() {
        when(conversionsRepository.findByTargetUnit(any(Unit.class))).thenReturn(conversionTestList);
        when(unitService.getUnitById(any(Integer.class))).thenReturn(targetTestUnit);
        List<Conversion> result = conversionServiceImplementation.getConversionsByTargetUnitId(targetTestUnit.getId());
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(unitService).getUnitById(1);
        verify(conversionsRepository).findByTargetUnit(targetTestUnit);
    }

    @Test
    void getConversionBySourceUnitIdAndTargetUnitId() {
        when(conversionsRepository.findByTargetUnitAndSourceUnit(any(Unit.class),any(Unit.class))).thenReturn(java.util.Optional.ofNullable(testConversion));
        when(unitService.getUnitById(any(Integer.class))).thenReturn(targetTestUnit);
        Conversion result = conversionServiceImplementation.getConversionBySourceUnitIdAndTargetUnitId(sourceTestUnit.getId(),targetTestUnit.getId());
        assertNotNull(result);
        assertEquals(1000D, result.getConversionFactor());
    }

    @Test
    void getConversionById() {
        when(conversionsRepository.findById(any(Integer.class))).thenReturn(java.util.Optional.ofNullable(testConversion));
        Conversion result = conversionServiceImplementation.getConversionById(1);
        assertNotNull(result);
        assertEquals(1000D, result.getConversionFactor());
        verify(conversionsRepository).findById(1);
    }

    @Test
    void saveOrUpdateConversion() {
        when(conversionsRepository.save(any(Conversion.class))).thenReturn(testConversion);
        when(unitService.getUnitById(any(Integer.class))).thenReturn(targetTestUnit);
        Conversion result = conversionServiceImplementation.saveOrUpdateConversion(testConversion.toVO());
        assertNotNull(result);
        assertEquals(1000D, result.getConversionFactor());
    }
}