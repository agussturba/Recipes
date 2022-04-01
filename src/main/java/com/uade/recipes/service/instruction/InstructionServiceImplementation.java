package com.uade.recipes.service.instruction;

import com.uade.recipes.exceptions.InstructionNotFoundException;
import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.model.Instruction;
import com.uade.recipes.model.Recipe;
import com.uade.recipes.persistance.InstructionRepository;
import com.uade.recipes.persistance.RecipeRepository;
import com.uade.recipes.vo.InstructionVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructionServiceImplementation implements InstructionService {
    private final InstructionRepository instructionRepository;
    private final RecipeRepository recipeRepository;

    public InstructionServiceImplementation(InstructionRepository instructionRepository, RecipeRepository recipeRepository) {
        this.instructionRepository = instructionRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Instruction getInstructionById(Integer id) {
        return instructionRepository.findById(id).orElseThrow(InstructionNotFoundException::new);
    }

    @Override
    public List<Instruction> getAllInstructions() {
        return instructionRepository.findAll();
    }

    @Override
    public List<Instruction> getInstructionsByRecipeId(Integer recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(RecipeNotFoundException::new);
        return instructionRepository.findByRecipe(recipe);
    }

    @Override
    public Instruction saveOrUpdateInstruction(InstructionVo instructionVo) {
        Recipe recipe = recipeRepository.findById(instructionVo.getRecipeId()).orElseThrow(RecipeNotFoundException::new);
        Instruction instruction = new Instruction();
        if (instructionVo.getId() != null) {
            this.getInstructionById(instruction.getId());
            instruction.setId(instructionVo.getId());
        }
        instruction.setDescription(instructionVo.getDescription());
        instruction.setPhoto(instructionVo.getPhoto());
        instruction.setVideo(instructionVo.getVideo());
        instruction.setRecipe(recipe);
        return instructionRepository.save(instruction);
    }
}
