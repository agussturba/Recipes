package com.uade.recipes.vo;

import com.uade.recipes.model.Instruction;
import com.uade.recipes.model.Recipe;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstructionVo {
    Integer id;
    String description;
    Integer recipeId;
    Integer numberOfStep;
    String title;

    public Instruction toModel(Recipe recipe){
        Instruction instruction = new Instruction();
        instruction.setTitle(title);
        instruction.setId(this.getId());
        instruction.setDescription(this.getDescription());
        instruction.setNumberOfStep(numberOfStep);
        instruction.setRecipe(recipe);
        return instruction;
    }
}
