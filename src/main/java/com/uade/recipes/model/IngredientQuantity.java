package com.uade.recipes.model;

import com.uade.recipes.vo.IngredientQuantityVo;
import com.uade.recipes.vo.IngredientVo;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Data
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
    private Double quantity;
    @OneToOne
    private Unit unit;
    private String observations;


    public IngredientQuantity(Ingredient ingredient, Double quantity) {
        this.ingredient = ingredient;
        this.quantity = quantity;
    }

    public IngredientQuantityVo toVO(){
        IngredientQuantityVo vo = new IngredientQuantityVo();
        vo.setIngredientId(ingredient.getId());
        vo.setRecipeId(recipe.getId());
        vo.setObservations(observations);
        vo.setQuantity(quantity);

        return vo;
    }
}
