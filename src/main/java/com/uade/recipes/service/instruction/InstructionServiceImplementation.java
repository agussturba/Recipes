package com.uade.recipes.service.instruction;

import com.uade.recipes.exceptions.instructionExceptions.InstructionNotFoundException;
import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.model.Instruction;
import com.uade.recipes.model.Recipe;
import com.uade.recipes.persistance.InstructionRepository;
import com.uade.recipes.service.recipe.RecipeService;
import com.uade.recipes.vo.InstructionVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public List<Instruction> getAllInstructions() {
        return (List<Instruction>) instructionRepository.findAll();
    }

    @Override
    public List<Instruction> getInstructionsByRecipeId(Integer recipeId) {
        //return instructionRepository.findByRecipe(recipe);
        return null;
    }

    @Override
    public Instruction saveOrUpdateInstruction(InstructionVo instructionVo) throws RecipeNotFoundException {
        Recipe recipe = recipeService.getRecipeById(instructionVo.getRecipeId());
        return instructionRepository.save(instructionVo.toModel(recipe));
    }

    private List<Instruction> getInstructionsByIds(List<Integer> instructionIdList) throws InstructionNotFoundException {
        List<Instruction> instructions = new ArrayList<>();
        for (Integer instructionId : instructionIdList) {
            Instruction instruction = this.getInstructionById(instructionId);
            instructions.add(instruction);
        }
        return instructions;
    }
}

