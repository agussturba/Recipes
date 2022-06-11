package com.uade.recipes.vo;

import com.uade.recipes.model.Ingredient;
import com.uade.recipes.model.Type;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class IngredientVo {
    Integer id;
    String name;
    boolean dividable;

    public Ingredient toModel() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(this.getId());
        ingredient.setName(this.getName());
        ingredient.setDividable(dividable);
        return ingredient;
    }
}
