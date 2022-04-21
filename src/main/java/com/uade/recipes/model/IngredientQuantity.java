package com.uade.recipes.model;

import com.uade.recipes.vo.IngredientQuantityVo;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
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
        vo.setIngredientId(ingredient.getId());
        vo.setRecipeId(recipe.getId());
        vo.setObservations(observations);
        vo.setQuantity(quantity);

        return vo;
    }
    public boolean isIngredientDividable(){
        return ingredient.isDividable();
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
