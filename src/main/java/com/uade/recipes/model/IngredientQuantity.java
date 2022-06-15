package com.uade.recipes.model;

import com.uade.recipes.vo.IngredientQuantityVo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.Objects;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class IngredientQuantity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne
    private Ingredient ingredient;
    @ManyToOne
    private Recipe recipe;
    @Column(nullable = false)
    private Double quantity;
    @OneToOne
    private Unit unit;
    private String observations;


    public IngredientQuantityVo toVO() {
        IngredientQuantityVo vo = new IngredientQuantityVo();
        vo.setId(id);
        vo.setIngredientName(ingredient.getName());
        vo.setIngredientId(ingredient.getId());
        vo.setUnitName(unit.getDescription());
        vo.setRecipeId(recipe.getId());
        vo.setObservations(observations);
        vo.setQuantity(quantity);
        vo.setUnitId(unit.getId());

        return vo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        IngredientQuantity that = (IngredientQuantity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
