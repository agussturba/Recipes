package com.uade.recipes.service.type;

import com.uade.recipes.model.Type;
import com.uade.recipes.persistance.TypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TypeServiceImplementationTest {

    @Mock
    private TypeRepository typeRepository;
    @InjectMocks
    private TypeServiceImplementation typeService;

    private Type testType;
    private List<Type> typeTestList;

    @BeforeEach
    void setUp() {
        typeTestList = new ArrayList<>();
        testType = new Type("Italiana");
        testType.setId(1);
        typeTestList.add(testType);
    }


    @Test
    void getAllTypes() {
        Mockito.when(typeRepository.findAll()).thenReturn(typeTestList);
        List<Type> typeList = typeService.getAllTypes();
        assertNotNull(typeList);
        assertEquals(1, typeList.size());
        verify(typeRepository).findAll();
    }

    @Test
    void getTypeByDescription() {
        Mockito.when(typeRepository.findByDescription(any(String.class))).thenReturn(java.util.Optional.ofNullable(testType));
        Type testType = typeService.getTypeByDescription("este NASHEEEE");
        assertNotNull(testType);
        assertEquals("Italiana",testType.getDescription());
        verify(typeRepository).findByDescription("este NASHEEEE");
    }

    @Test
    void getTypeById() {
        Mockito.when(typeRepository.findById(any(Integer.class))).thenReturn(java.util.Optional.ofNullable(testType));
        Type testType = typeService.getTypeById(1);
        assertNotNull(testType);
        assertEquals("Italiana",testType.getDescription());
        verify(typeRepository).findById(1);
    }

    @Test
    void saveOrUpdateType() {
        Mockito.when(typeRepository.save(any(Type.class))).thenReturn(testType);
        Type type = new Type();
        type.setDescription("Italiana");
        Type testType2=typeRepository.save(type);
        verify(typeRepository).save(type);
        assertNotNull(testType2);
        assertEquals("Italiana",testType2.getDescription());
    }

    @Test
    void getTypesByIdList() {//Puede que difiera dependiendo de tu BD
        Mockito.when(typeRepository.findAllById(any(List.class))).thenReturn(typeTestList);
        List<Integer> idList = Arrays.asList(1,2);
        List<Type> typeList = typeService.getTypesByIdList(idList);
        assertNotNull(typeList);
        assertEquals(1, typeList.size());
        verify(typeRepository).findAllById(idList);
    }
}