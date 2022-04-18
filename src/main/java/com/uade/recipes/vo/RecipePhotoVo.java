package com.uade.recipes.vo;

import com.uade.recipes.model.Recipe;
import com.uade.recipes.model.RecipePhoto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipePhotoVo {
     Integer id;
     Integer recipeId;
     String photoUrl;
     String extension;
     public RecipePhoto toModel(Recipe recipe){
          RecipePhoto recipePhoto = new RecipePhoto();
          recipePhoto.setRecipe(recipe);
          recipePhoto.setPhotoUrl(getPhotoUrl());
          recipePhoto.setExtension(getExtension());
          recipePhoto.setId(getId());
          return recipePhoto;
     }
}
