package com.uade.recipes.service.unit;

import com.uade.recipes.model.Unit;
import com.uade.recipes.model.User;
import com.uade.recipes.persistance.UnitRepository;
import com.uade.recipes.persistance.UserRepository;
import com.uade.recipes.service.user.UserServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UnitServiceImplementationTest {
    @Mock
    private UnitRepository unitRepository;

    @InjectMocks
    private UnitServiceImplementation unitServiceImplementation;

    private Unit testUnit;
    private List<Unit> unitTestList;

    @BeforeEach
    void setUp() {
        unitTestList = new ArrayList<>();
        testUnit = new Unit();
        testUnit.setId(1);
        testUnit.setDescription("KG");
        unitTestList.add(testUnit);
    }

    @Test
    void getAllUnits() {
        when(unitRepository.findAll()).thenReturn(unitTestList);
        List<Unit> unitList = unitServiceImplementation.getAllUnits();
        assertNotNull(unitList);
        assertEquals(1, unitList.size());
        verify(unitRepository).findAll();
    }

    @Test
    void getUnitByDescription() {
        when(unitRepository.findByDescription(any(String.class))).thenReturn(java.util.Optional.ofNullable(testUnit));
        Unit unit = unitServiceImplementation.getUnitByDescription("KG");
        assertNotNull(unit);
        assertEquals("KG", unit.getDescription());
        verify(unitRepository).findByDescription("KG");
    }

    @Test
    void getUnitById() {
        when(unitRepository.findById(any(Integer.class))).thenReturn(java.util.Optional.ofNullable(testUnit));
        Unit unit = unitServiceImplementation.getUnitById(1);
        assertNotNull(unit);
        assertEquals("KG", unit.getDescription());
        verify(unitRepository).findById(1);
    }

    @Test
    void saveOrUpdateUnit() {
        when(unitRepository.save(any(Unit.class))).thenReturn(testUnit);
        Unit unit = unitServiceImplementation.saveOrUpdateUnit(testUnit.toVO());
        assertNotNull(unit);
        assertEquals("KG", unit.getDescription());
        verify(unitRepository).save(testUnit);
    }
}