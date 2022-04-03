package com.uade.recipes.vo;

import com.uade.recipes.model.Instruction;
import com.uade.recipes.model.Recipe;

public class InstructionVo {
    Integer recipeId;
    Integer id;
    String description;
    String photo;
    String video;

    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Instruction toModel(Recipe recipe){
        Instruction instruction = new Instruction();
        instruction.setId(this.getId());
        instruction.setDescription(this.getDescription());
        instruction.setPhoto(this.getPhoto());
        instruction.setVideo(this.getVideo());
        instruction.setRecipe(recipe);
        return instruction;
    }
}
