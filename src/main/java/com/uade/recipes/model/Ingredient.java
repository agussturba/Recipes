package com.uade.recipes.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class Ingredient {
    @Transient
    public static final String SEQUENCE_NAME = "ingredients_sequence";
    @Id
    private Integer id;
    private String name;
    private String type;

    public Ingredient() {
    }

    public Ingredient(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
