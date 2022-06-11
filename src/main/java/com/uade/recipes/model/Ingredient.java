package com.uade.recipes.model;

import com.uade.recipes.vo.IngredientVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Entity
@Getter
@Setter
@ToString
@SecondaryTable(name = "ingredient_addition", pkJoinColumns = @PrimaryKeyJoinColumn(name = "ingredient_id"))
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    /**
     * Atributo para verificar que el ingrediento puede ser divisible.Que el ingrediente puede ser la mitadd
     * de dicho elemento. IE un tomate es divisble porque podes trabajar con medio tomate pero un huevo no es
     * divisible.
     */
    @Column(table = "ingredient_addition")
    private Boolean dividable;

    public IngredientVo toVO() {
        IngredientVo vo = new IngredientVo();
        vo.setId(id);
        vo.setName(name);
        vo.setDividable(dividable);
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
