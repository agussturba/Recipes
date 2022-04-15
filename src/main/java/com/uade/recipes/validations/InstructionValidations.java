package com.uade.recipes.validations;

import com.uade.recipes.vo.InstructionVo;

import static com.uade.recipes.validations.IdValidations.validateRecipeId;

public class InstructionValidations {
    public static void validateInstructionData(InstructionVo instructionVo) {
        validateRecipeId(instructionVo.getRecipeId());
    }
}
