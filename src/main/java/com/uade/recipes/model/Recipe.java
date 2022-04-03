package com.uade.recipes.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Recipe {
    @Transient
    public static final String SEQUENCE_NAME = "recipe_sequence";
    @Id
    private Integer id;
    private String name;
    private String description;
    private List<String> photos;
    private List<IngredientQuantity> ingredientQuantityList;
    private List<Instruction> instructions;//TODO FIX THIS BUSCAR UNA FORMA DE AGREGAR FOTOS O VIDEOS EN LOS PASOS DEJO A PRIORI ASI
    private Dish dish;
    private User user;

    public Recipe() {
    }

    public Recipe(String name, String description, List<String> photos, List<IngredientQuantity> ingredientQuantityList, List<Instruction> instructions, Dish dish, User user) {
        this.name = name;
        this.description = description;
        this.photos = photos;
        this.ingredientQuantityList = ingredientQuantityList;
        this.instructions = instructions;
        this.dish = dish;
        this.user = user;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public List<IngredientQuantity> getIngredients() {
        return ingredientQuantityList;
    }

    public void setIngredients(List<IngredientQuantity> ingredientQuantityList) {
        this.ingredientQuantityList = ingredientQuantityList;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<IngredientQuantity> getIngredientQuantityList() {
        return ingredientQuantityList;
    }

    public void setIngredientQuantityList(List<IngredientQuantity> ingredientQuantityList) {
        this.ingredientQuantityList = ingredientQuantityList;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getLabels() {
        return dish.getLabels();
    }

}
