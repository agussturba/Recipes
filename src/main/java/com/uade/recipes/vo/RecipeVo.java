package com.uade.recipes.vo;

import com.uade.recipes.model.*;

import java.util.List;

public class RecipeVo {
    Integer id;
    String name;
    String description;
    List<String> photos;
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

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
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

    public Recipe toModel(User user, Dish dish, List<Instruction> instructions, List<IngredientQuantity> ingredientQuantityList) {
        Recipe recipe = new Recipe();
        recipe.setId(getId());
        recipe.setName(getName());
        recipe.setDescription(getDescription());
        recipe.setPhotos(getPhotos());
        recipe.setIngredientQuantityList(ingredientQuantityList);
        recipe.setInstructions(instructions);
        recipe.setDish(dish);
        recipe.setUser(user);
        return recipe;
    }
}
