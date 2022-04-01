package com.uade.recipes.service.instruction;

import com.uade.recipes.model.Instruction;
import com.uade.recipes.vo.InstructionVo;

import java.util.List;

public interface InstructionService {
    Instruction getInstructionById(Integer id);
    List<Instruction> getAllInstructions();
    List<Instruction> getInstructionsByRecipeId(Integer recipeId);
    Instruction saveOrUpdateInstruction(InstructionVo instructionVo);
}
