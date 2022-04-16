package com.uade.recipes.model;

import com.uade.recipes.vo.IngredientVo;
import lombok.Data;

import javax.persistence.*;


@Entity
@Data
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @OneToOne
    private Type type;

    public IngredientVo toVO(){
        IngredientVo vo = new IngredientVo();
        vo.setName(name);
        vo.setTypeId(type.getId());

        return vo;
    }

}
