package com.uade.recipes.service.instruction;

import com.uade.recipes.exceptions.instructionExceptions.InstructionNotFoundException;
import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.model.Instruction;
import com.uade.recipes.vo.InstructionVo;

import java.util.List;

public interface InstructionService {
    Instruction getInstructionById(Integer id) throws InstructionNotFoundException;

    List<Instruction> getInstructionsByRecipeId(Integer recipeId) throws RecipeNotFoundException;

    Instruction saveOrUpdateInstruction(InstructionVo instructionVo) throws RecipeNotFoundException;

    void deleteInstructionByRecipeIdAndNumberOfStep(Integer recipeId, Integer numberStep) throws RecipeNotFoundException;

    void deleteAllInstructionsByRecipeId(Integer recipeId) throws RecipeNotFoundException;

    Instruction getInstructionByRecipeIdAndNumberOfStep(Integer recipeId, Integer numberStep) throws RecipeNotFoundException;

}
