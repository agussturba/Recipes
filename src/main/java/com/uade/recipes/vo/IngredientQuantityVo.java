package com.uade.recipes.vo;

import com.uade.recipes.model.Ingredient;
import com.uade.recipes.model.IngredientQuantity;
import com.uade.recipes.model.Recipe;
import com.uade.recipes.model.Unit;
import lombok.Data;

@Data
public class IngredientQuantityVo {
    Integer id;
    Integer ingredientId;
    Integer unitId;
    Double quantity;
    Integer recipeId;
    String observations;
    public IngredientQuantity toModel(Recipe recipe, Ingredient ingredient, Unit unit){
        IngredientQuantity ingredientQuantity=new IngredientQuantity();
        ingredientQuantity.setIngredient(ingredient);
        ingredientQuantity.setQuantity(quantity);
        ingredientQuantity.setObservations(observations);
        ingredientQuantity.setRecipe(recipe);
        ingredientQuantity.setUnit(unit);
        return ingredientQuantity;
    }
}
