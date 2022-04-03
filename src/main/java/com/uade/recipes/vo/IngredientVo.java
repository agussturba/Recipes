package com.uade.recipes.vo;

import com.uade.recipes.model.Ingredient;

public class IngredientVo {
    Integer id;
    String name;
    String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Ingredient toModel() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(this.getId());
        ingredient.setName(this.getName());
        ingredient.setType(this.getName());
        return ingredient;
    }
}
