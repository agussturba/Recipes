package com.uade.recipes.service.dish;

import com.uade.recipes.exceptions.dishExceptions.DishNameContainsNumberException;
import com.uade.recipes.exceptions.dishExceptions.DishNotFoundException;
import com.uade.recipes.exceptions.dishExceptions.DishTypeContainsNumberException;
import com.uade.recipes.model.Dish;
import com.uade.recipes.model.Type;
import com.uade.recipes.persistance.DishRepository;
import com.uade.recipes.persistance.TypeRepository;
import com.uade.recipes.service.type.TypeServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DishServiceImplementationTest {
    @Mock
    private DishRepository dishRepository;
    @Mock
    private TypeServiceImplementation typeService;
    @InjectMocks
    private DishServiceImplementation dishServiceImplementation;


    private Dish testDish;
    private Type testType;
    private List<Dish> dishTestList;

    @BeforeEach
    void setUp() {
        dishTestList = new ArrayList<>();
        testType = new Type("Italiana");
        testType.setId(1);
        testDish = new Dish("Pizza", testType);
        dishTestList.add(testDish);
    }

    @Test
    void getAllDishes() {
        when(dishRepository.findAll()).thenReturn(dishTestList);
        List<Dish> result = dishServiceImplementation.getAllDishes();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(dishRepository).findAll();
    }

    @Test
    void getDishById() throws DishNotFoundException {
        when(dishRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(testDish));
        Dish result = dishServiceImplementation.getDishById(1);
        assertNotNull(result);
        assertEquals("Pizza", result.getName());
        verify(dishRepository).findById(1);
    }

    @Test
    void getDishByIdNotFoundCase() {
        when(dishRepository.findById(any(Integer.class))).thenThrow(DishNotFoundException.class);
        assertThrows(DishNotFoundException.class, () -> dishServiceImplementation.getDishById(1));
        verify(dishRepository).findById(1);
    }

    @Test
    void getDishByName() {
        when(dishRepository.findByName(any(String.class))).thenReturn(Optional.ofNullable(testDish));
        Dish result = dishServiceImplementation.getDishByName("plato");
        assertNotNull(result);
        assertEquals("Pizza", result.getName());
        verify(dishRepository).findByName("plato");
    }

    @Test
    void getDishByNameNotFoundCase() {
        when(dishRepository.findByName(any(String.class))).thenThrow(DishNotFoundException.class);
        assertThrows(DishNotFoundException.class, () -> dishServiceImplementation.getDishByName("plato"));
        verify(dishRepository).findByName("plato");
    }

    @Test
    void getDishesByTypeIdList() {
        when(dishRepository.findByTypeIsIn(any(List.class))).thenReturn(dishTestList);
        List<Dish> result = dishServiceImplementation.getDishesByTypeIdList(Collections.singletonList(1));
        assertNotNull(result);
        assertEquals(1, result.size());

    }

    @Test
    void saveOrUpdateDish() throws DishNameContainsNumberException, DishTypeContainsNumberException {
        when(dishRepository.save(any(Dish.class))).thenReturn(testDish);
        when(typeService.getTypeById(any(Integer.class))).thenReturn(testType);
        Dish result = dishServiceImplementation.saveOrUpdateDish(testDish.toVO());
        assertNotNull(result);
        assertEquals("Pizza", result.getName());

    }
}