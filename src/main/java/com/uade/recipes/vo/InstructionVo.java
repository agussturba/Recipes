package com.uade.recipes.vo;

import com.uade.recipes.model.Instruction;
import com.uade.recipes.model.Recipe;
import lombok.Data;

@Data
public class InstructionVo {
    Integer id;
    String description;
    Integer recipeId;
    Integer numberOfStep;

    public Instruction toModel(Recipe recipe,Integer numberOfStep){
        Instruction instruction = new Instruction();
        instruction.setId(this.getId());
        instruction.setDescription(this.getDescription());
        instruction.setNumberOfStep(numberOfStep);
        instruction.setRecipe(recipe);
        return instruction;
    }
}
