package com.uade.recipes.service.instruction;

import com.uade.recipes.exceptions.instructionExceptions.InstructionNotFoundException;
import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.model.*;
import com.uade.recipes.persistance.InstructionRepository;
import com.uade.recipes.service.recipe.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InstructionServiceImplementationTest {
    @Mock
    private InstructionRepository instructionRepository;
    @Mock
    private RecipeService recipeService;
    @InjectMocks
    private InstructionServiceImplementation instructionServiceImplementation;

    private Instruction testInstruction;
    private List<Instruction> testInstructionList;
    private Recipe testRecipe;
    private User testUser;
    private Type testType;
    private RecipePhoto testRecipePhoto;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1);
        testUser.setUserName("agussturba");
        testUser.setEnabled(true);
        testUser.setAvatar(null);
        testUser.setName("Agustin");
        testUser.setEmail("agus@mail.com");
        testUser.setPassword("12345");

        testRecipePhoto = new RecipePhoto();

        testType = new Type("Italiana");
        testType.setId(1);

        testRecipe = new Recipe();
        testRecipe.setId(1);
        testRecipe.setTimestamp(LocalDateTime.now());
        testRecipe.setDescription("Receta de Papa");
        testRecipe.setEnabled(true);
        testRecipe.setRecipePhoto(Arrays.asList(testRecipePhoto));
        testRecipe.setName("PAPAS");
        testRecipe.setDuration(2);
        testRecipe.setPortions(3D);
        testRecipe.setPeopleAmount(2);
        testRecipe.setType(testType);
        testRecipe.setUser(testUser);


        testInstructionList = new ArrayList<>();
        testInstruction = new Instruction();
        testInstruction.setDescription("CORTA LA PAPA BOLUDAZO");
        testInstruction.setRecipe(testRecipe);
        testInstruction.setNumberOfStep(1);
        testInstructionList.add(testInstruction);

    }

    @Test
    void getInstructionById() throws InstructionNotFoundException {
        when(instructionRepository.findById(any(Integer.class))).thenReturn(java.util.Optional.ofNullable(testInstruction));
        Instruction instruction = instructionServiceImplementation.getInstructionById(1);
        assertNotNull(instruction);
        assertEquals("CORTA LA PAPA BOLUDAZO", instruction.getDescription());
        verify(instructionRepository).findById(1);
    }

    @Test
    void getAllInstructions() {
        when(instructionRepository.findAll()).thenReturn(testInstructionList);
        List<Instruction> instructionList = instructionServiceImplementation.getAllInstructions();
        assertNotNull(instructionList);
        assertEquals(1, instructionList.size());
        verify(instructionRepository).findAll();
    }

    @Test
    void getInstructionsByRecipeId() throws RecipeNotFoundException {
        when(recipeService.getRecipeById(any(Integer.class))).thenReturn(testRecipe);
        when(instructionRepository.findByRecipe(any(Recipe.class))).thenReturn(testInstructionList);
        List<Instruction> instructionList = instructionServiceImplementation.getInstructionsByRecipeId(1);
        assertNotNull(instructionList);
        assertEquals(1, instructionList.size());
        verify(instructionRepository).findByRecipe(testRecipe);
    }

    @Test
    void saveOrUpdateInstruction() throws RecipeNotFoundException {
        when(instructionRepository.findFirstByRecipeOrderByNumberOfStepDesc(any(Recipe.class))).thenReturn(testInstruction);
        when(recipeService.getRecipeById(any(Integer.class))).thenReturn(testRecipe);
        when(instructionRepository.save(any(Instruction.class))).thenReturn(testInstruction);
        Instruction instruction = instructionServiceImplementation.saveOrUpdateInstruction(testInstruction.toVO());
        assertNotNull(instruction);
        assertEquals("CORTA LA PAPA BOLUDAZO", instruction.getDescription());
    }
}