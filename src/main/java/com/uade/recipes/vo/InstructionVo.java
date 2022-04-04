package com.uade.recipes.vo;

import com.uade.recipes.model.Instruction;
import com.uade.recipes.model.Recipe;
import lombok.Data;

@Data
public class InstructionVo {
    Integer id;
    String description;
    String photo;
    String video;


    public Instruction toModel(){
        Instruction instruction = new Instruction();
        instruction.setId(this.getId());
        instruction.setDescription(this.getDescription());
        instruction.setPhoto(this.getPhoto());
        instruction.setVideo(this.getVideo());
        return instruction;
    }
}
