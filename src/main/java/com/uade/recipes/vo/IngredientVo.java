package com.uade.recipes.vo;

import com.uade.recipes.model.Ingredient;
import lombok.Data;

@Data
public class IngredientVo {
    Integer id;
    String name;
    String type;


    public Ingredient toModel() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(this.getId());
        ingredient.setName(this.getName());
        ingredient.setType(this.getName());
        return ingredient;
    }
}
