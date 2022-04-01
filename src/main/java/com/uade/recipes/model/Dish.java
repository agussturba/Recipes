package com.uade.recipes.model;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

@Document
public class Dish {//Los platos para los burros
    @Id
    private Integer id;
    private String name;
    private String type;

    public Dish() {
    }

    public Dish(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
