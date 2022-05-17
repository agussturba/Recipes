package com.uade.recipes.service.instruction;

import com.uade.recipes.model.*;
import com.uade.recipes.persistance.InstructionRepository;
import com.uade.recipes.service.recipe.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        testRecipe.setTimestamp(LocalDateTime.now());
        testRecipe.setDescription("Receta de Papa");
        testRecipe.setEnabled(true);
        testRecipe.setRecipePhoto(testRecipePhoto);
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
    void getInstructionById() {
    }

    @Test
    void getAllInstructions() {
    }

    @Test
    void getInstructionsByRecipeId() {
    }

    @Test
    void saveOrUpdateInstruction() {
    }
}