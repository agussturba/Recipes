package com.uade.recipes.model;

import com.uade.recipes.vo.IngredientVo;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Getter
@Setter
@ToString
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
        vo.setId(id);
        vo.setName(name);
        vo.setTypeId(type.getId());

        return vo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Ingredient that = (Ingredient) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
