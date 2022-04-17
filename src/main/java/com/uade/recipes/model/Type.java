package com.uade.recipes.model;

import com.uade.recipes.vo.TypeVo;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "type")
@Data
@NoArgsConstructor
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String description;

    public Type(String description) {
        this.description = description;
    }

    public TypeVo toVO() {
        TypeVo vo = new TypeVo();
        vo.setDescription(description);
        return vo;
    }

}
