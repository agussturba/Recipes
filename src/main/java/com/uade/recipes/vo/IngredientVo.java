package com.uade.recipes.vo;

import com.uade.recipes.model.Ingredient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngredientVo {
    Integer id;
    String name;

    public Ingredient toModel() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(this.getId());
        ingredient.setName(this.getName());
        return ingredient;
    }
}
