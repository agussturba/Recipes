package com.uade.recipes.model;

import com.uade.recipes.vo.TypeVo;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="type")
@Data
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(nullable = false,unique = true)
    private String description;

    public TypeVo toVO(){
        TypeVo vo = new TypeVo();
        vo.setDescription(description);
        return vo;
    }

}
