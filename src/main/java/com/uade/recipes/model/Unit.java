package com.uade.recipes.model;

import com.uade.recipes.vo.UnitVo;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String description;

    public UnitVo toVO(){
        UnitVo vo = new UnitVo();
        vo.setDescription(description);

        return vo;
    }
}