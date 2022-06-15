package com.uade.recipes.service.instruction;

import com.uade.recipes.exceptions.instructionExceptions.InstructionExistsException;
import com.uade.recipes.exceptions.instructionExceptions.InstructionNotFoundException;
import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.model.Instruction;
import com.uade.recipes.model.Recipe;
import com.uade.recipes.persistance.InstructionRepository;
import com.uade.recipes.service.recipe.RecipeService;
import com.uade.recipes.vo.InstructionVo;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.uade.recipes.validations.InstructionValidations.validateInstructionData;

@Service
public class InstructionServiceImplementation implements InstructionService {
    private final InstructionRepository instructionRepository;
    private final RecipeService recipeService;

    public InstructionServiceImplementation(InstructionRepository instructionRepository, RecipeService recipeService) {
        this.instructionRepository = instructionRepository;
        this.recipeService = recipeService;
    }

    @Override
    public Instruction getInstructionById(Integer id) throws InstructionNotFoundException {
        return instructionRepository.findById(id).orElseThrow(InstructionNotFoundException::new);
    }

    @Override
    public List<Instruction> getInstructionsByRecipeId(Integer recipeId) throws RecipeNotFoundException {
        Recipe recipe = recipeService.getRecipeById(recipeId);
        return instructionRepository.findByRecipe(recipe);
    }

    @Override
    public Instruction saveOrUpdateInstruction(InstructionVo instructionVo) throws RecipeNotFoundException {
        validateInstructionData(instructionVo);
        if(!instructionExist(instructionVo)) {
            Recipe recipe = recipeService.getRecipeById(instructionVo.getRecipeId());
            //Integer nextStep = getLastStepByRecipe(recipe) + 1;
            return instructionRepository.save(instructionVo.toModel(recipe));
        }
        else{
            throw new InstructionExistsException();
        }
    }

    @Override
    public void deleteInstructionByRecipeIdAndNumberOfStep(Integer recipeId, Integer numberOfStep) throws RecipeNotFoundException {
        Recipe recipe = recipeService.getRecipeById(recipeId);
        Instruction instruction = instructionRepository.findByRecipeAndNumberOfStep(recipe,numberOfStep);
        instructionRepository.delete(instruction);

    }

    @Override
    public void deleteAllInstructionsByRecipeId(Integer recipeId) throws RecipeNotFoundException {
        Recipe recipe = recipeService.getRecipeById(recipeId);
        List<Instruction> instructionList = instructionRepository.findByRecipe(recipe);
        instructionRepository.deleteAll(instructionList);
    }

    @Override
    public Instruction getInstructionByRecipeIdAndNumberOfStep(Integer recipeId, Integer numberOfStep) throws RecipeNotFoundException {
        Recipe recipe = recipeService.getRecipeById(recipeId);
        return instructionRepository.findByRecipeAndNumberOfStep(recipe,numberOfStep);
    }

    private boolean instructionExist(InstructionVo instructionVo) throws RecipeNotFoundException {
        Recipe recipe = recipeService.getRecipeById(instructionVo.getRecipeId());
        try {
            instructionRepository.findByRecipeAndNumberOfStep(recipe,instructionVo.getNumberOfStep());
            return true;
        }catch (Exception e){
            return false;
        }
    }

    private Integer getLastStepByRecipe(Recipe recipe) {
        return instructionRepository.findFirstByRecipeOrderByNumberOfStepDesc(recipe).getNumberOfStep();
    }
}

