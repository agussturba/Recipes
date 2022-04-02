package com.uade.recipes.vo;

import java.util.List;

public class RecipeVo {
    Integer id;
    String name;
    String description;
    String photos;
    List<Integer> ingredientQuantityIdList;
    List<Integer> instructionsIds;
    Integer dishId;
    Integer userId;


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


    public List<Integer> getIngredientQuantityIdList() {
        return ingredientQuantityIdList;
    }

    public void setIngredientQuantityIdList(List<Integer> ingredientQuantityIdList) {
        this.ingredientQuantityIdList = ingredientQuantityIdList;
    }

    public List<Integer> getInstructions() {
        return instructionsIds;
    }

    public void setInstructions(List<Integer> instructions) {
        this.instructionsIds = instructions;
    }

    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
