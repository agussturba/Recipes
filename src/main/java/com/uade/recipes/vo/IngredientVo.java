package com.uade.recipes.vo;

import com.uade.recipes.model.Ingredient;
import com.uade.recipes.model.Type;
import lombok.Data;

@Data
public class IngredientVo {
    Integer id;
    String name;
    Integer typeId;


    public Ingredient toModel(Type type) {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(this.getId());
        ingredient.setName(this.getName());
        ingredient.setType(type);
        return ingredient;
    }
}
