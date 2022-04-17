package com.uade.recipes.vo;

import com.uade.recipes.model.Dish;
import com.uade.recipes.model.Recipe;
import com.uade.recipes.model.RecipePhoto;
import com.uade.recipes.model.Type;
import com.uade.recipes.model.User;
import lombok.Data;

import java.util.List;

@Data
public class RecipeVo {
    Integer id;
    String name;
    String description;
    List<Integer> typeIdList;
    Integer dishId;
    Integer userId;
    Integer peopleAmount;
    Double portions;


    public Recipe toModel(User user, Dish dish) {
        Recipe recipe = new Recipe();
        recipe.setId(getId());
        recipe.setName(getName());
        recipe.setDescription(getDescription());
        recipe.setDish(dish);
        recipe.setUser(user);
        recipe.setPeopleAmount(peopleAmount);
        recipe.setType((List<Type>) dish.getTypes());
        recipe.setPortions(portions);
        return recipe;
    }
}
