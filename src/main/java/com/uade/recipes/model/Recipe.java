package com.uade.recipes.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String name;
    private String description;
    private String photos;//TODO BUSCAR COMO SE GUARDA LAS FOTOS EN UNA BASE DE DATOS
    @OneToMany
    private List<IngredientQuantity> ingredientQuantityList;//Si se puede, buscar una forma mas eficiente.
    private String instructions;//TODO FIX THIS BUSCAR UNA FORMA DE AGREGAR FOTOS O VIDEOS EN LOS PASOS DEJO A PRIORI ASI
    @ManyToOne
    private Dish dish;
    @ManyToOne
    private User user;
    private String type;//Maybe useless,remplazable con una query todo lo de esto es arreglable pero me dio paja.

    public Recipe(String name, String description, String photos, List<IngredientQuantity> ingredientQuantityList, String instructions, Dish dish, User user) {
        this.name = name;
        this.description = description;
        this.photos = photos;
        this.ingredientQuantityList = ingredientQuantityList;
        this.instructions = instructions;
        this.dish = dish;
        this.user = user;
        this.type = dish.getType();
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

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public List<IngredientQuantity> getIngredients() {
        return ingredientQuantityList;
    }

    public void setIngredients(List<IngredientQuantity> ingredientQuantityList) {
        this.ingredientQuantityList = ingredientQuantityList;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
