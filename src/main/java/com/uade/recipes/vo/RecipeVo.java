package com.uade.recipes.vo;

import com.uade.recipes.model.*;
import lombok.Data;

import java.util.List;
@Data
public class RecipeVo {
    Integer id;
    String name;
    String description;
    List<String> photos;
    List<Integer> ingredientQuantityIdList;
    List<Integer> instructionsIds;
    Integer dishId;
    Integer userId;

    public Recipe toModel(User user, Dish dish, List<Instruction> instructions, List<IngredientQuantity> ingredientQuantityList) {
        Recipe recipe = new Recipe();
        recipe.setId(getId());
        recipe.setName(getName());
        recipe.setDescription(getDescription());
        recipe.setPhotos(getPhotos());
        recipe.setIngredientQuantityList(ingredientQuantityList);
        recipe.setInstructions(instructions);
        recipe.setDish(dish);
        recipe.setUser(user);
        return recipe;
    }
}
