package com.uade.recipes.vo;

import com.uade.recipes.model.Ingredient;
import com.uade.recipes.model.IngredientQuantity;
import com.uade.recipes.model.Recipe;
import com.uade.recipes.model.Unit;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngredientQuantityVo {
    Integer id;
    Integer ingredientId;
    Integer unitId;
    String unitName;
    Double quantity;
    String ingredientName;
    Integer recipeId;
    String observations;

    public IngredientQuantity toModel(Recipe recipe, Ingredient ingredient, Unit unit) {
        IngredientQuantity ingredientQuantity = new IngredientQuantity();
        ingredientQuantity.setIngredient(ingredient);
        ingredientQuantity.setQuantity(quantity);
        ingredientQuantity.setObservations(observations);
        ingredientQuantity.setRecipe(recipe);
        ingredientQuantity.setUnit(unit);
        return ingredientQuantity;
    }
}
