package com.uade.recipes.vo;

import com.uade.recipes.model.Recipe;
import com.uade.recipes.model.Recipe_Addition;
import com.uade.recipes.model.User;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

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
    Timestamp timestamp;
    Boolean enabled;


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
    public Recipe_Addition getAdditionalInformation(){
        Recipe_Addition recipe_addition = new Recipe_Addition();
        recipe_addition.setEnabled(enabled);
        recipe_addition.setTimestamp(timestamp);
        return recipe_addition;
    }
}
