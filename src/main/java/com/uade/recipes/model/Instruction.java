package com.uade.recipes.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class Instruction {
    @Transient
    public static final String SEQUENCE_NAME = "instruction_sequence";
    @Id
    private Integer id;
    private String description;
    private String photo;
    private String video;
    private Recipe recipe; //TODO REVISAR SI ESTA BIEN ESTA DEPENDENCIA

    public Instruction(String description, String photo, String video, Recipe recipe) {
        this.description = description;
        this.photo = photo;
        this.video = video;
        this.recipe = recipe;
    }

    public Instruction() {

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

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
