package com.uade.recipes.vo;

import com.uade.recipes.model.Dish;
import com.uade.recipes.model.Recipe;
import com.uade.recipes.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeVo {
    Integer id;
    String name;
    String description;
    Integer typeId;
    Integer dishId;
    Integer userId;
    Integer peopleAmount;
    Double portions;
    Integer duration;


    public Recipe toModel(User user) {
        Recipe recipe = new Recipe();
        recipe.setId(getId());
        recipe.setName(getName());
        recipe.setDescription(getDescription());
        recipe.setUser(user);
        recipe.setPeopleAmount(peopleAmount);
        recipe.setPortions(portions);
        recipe.setDuration(duration);
        return recipe;
    }
}
