package com.uade.recipes.vo;

import com.uade.recipes.model.Recipe;
import com.uade.recipes.model.Type;
import com.uade.recipes.model.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RecipeVo {
    Integer id;
    String name;
    String description;
    Integer typeId;
    String typeName;
    Integer ownerId;
    String ownerUserName;
    String mainPhoto;
    Integer peopleAmount;
    Double portions;
    Integer duration;
    LocalDateTime timestamp;
    Boolean enabled;



    public Recipe toModel(User owner, Type type) {
        Recipe recipe = new Recipe();
        if (id != null) {
            recipe.setId(getId());
        }
        recipe.setName(getName());
        recipe.setDescription(getDescription());
        recipe.setOwner(owner);
        recipe.setPeopleAmount(peopleAmount);
        recipe.setPortions(portions);
        recipe.setDuration(duration);
        recipe.setMainPhoto(mainPhoto);
        recipe.setTimestamp(timestamp);
        recipe.setEnabled(enabled);
        recipe.setType(type);
        return recipe;
    }

}
