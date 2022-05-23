package com.uade.recipes.vo;

import com.uade.recipes.model.Recipe;
import com.uade.recipes.model.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class RecipeVo {
    Integer id;
    String name;
    String description;
    Integer typeId;
    Integer dishId;
    Integer ownerId;
    List<Integer> recipePhotoIdList;
    Integer peopleAmount;
    Double portions;
    Integer duration;
    LocalDateTime timestamp;
    Boolean enabled;


    public Recipe toModel(User owner) {
        Recipe recipe = new Recipe();
        recipe.setId(getId());
        recipe.setName(getName());
        recipe.setDescription(getDescription());
        recipe.setOwner(owner);
        recipe.setPeopleAmount(peopleAmount);
        recipe.setPortions(portions);
        recipe.setDuration(duration);
        recipe.setTimestamp(timestamp);
        recipe.setEnabled(enabled);
        return recipe;
    }

}
