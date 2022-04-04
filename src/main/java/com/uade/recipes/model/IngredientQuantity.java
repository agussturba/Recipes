package com.uade.recipes.model;

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
    private Double quantity;

    public IngredientQuantity(Ingredient ingredient, Double quantity) {
        this.ingredient = ingredient;
        this.quantity = quantity;
    }
}
