package com.uade.recipes.model;

import com.uade.recipes.vo.InstructionVo;
import lombok.Data;

import javax.persistence.*;


@Entity
@Data
public class Instruction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne
    private Recipe recipe;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Integer numberOfStep;

    public InstructionVo toVO(){
        InstructionVo vo = new InstructionVo();
        vo.setRecipeId(recipe.getId());
        vo.setDescription(description);
        vo.setNumberOfStep(numberOfStep);

        return vo;
    }

}
