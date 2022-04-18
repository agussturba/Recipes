package com.uade.recipes.vo;

import com.uade.recipes.model.Instruction;
import com.uade.recipes.model.Multimedia;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MultimediaVo {
     Integer id;
     Integer instructionId;
     String typeContent;
     String extension;
     String urlContent;
     public Multimedia toModel(Instruction instruction){
         Multimedia multimedia= new Multimedia();
         multimedia.setId(id);
         multimedia.setExtension(extension);
         multimedia.setTypeContent(typeContent);
         multimedia.setInstruction(instruction);
         multimedia.setUrlContent(urlContent);
         return multimedia;
     }
}
