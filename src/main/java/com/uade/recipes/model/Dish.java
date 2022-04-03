package com.uade.recipes.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document
public class Dish {
    @Transient
    public static final String SEQUENCE_NAME = "dishes_sequence";
    @Id
    private Integer id;
    private String name;
    private List<String> labels;

    public Dish() {
    }

    public Dish(String name, List<String> labels) {
        this.name = name;
        this.labels = labels;
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

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }
}
