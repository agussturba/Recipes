package com.uade.recipes.model;

import com.uade.recipes.vo.MultimediaVo;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Multimedia {
    @Id
    @GeneratedValue
    private Integer id;
    @OneToOne
    private Instruction instruction;
    private String typeContent;
    private String extension;
    private String urlContent;

    public MultimediaVo toVO(){
        MultimediaVo vo = new MultimediaVo();
        vo.setId(id);
        vo.setInstructionId(instruction.getId());
        vo.setTypeContent(typeContent);
        vo.setUrlContent(urlContent);
        vo.setExtension(extension);

        return vo;
    }
}
