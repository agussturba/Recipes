package com.uade.recipes.vo;

import com.uade.recipes.model.Photo;
import com.uade.recipes.model.Recipe;
import lombok.Data;

@Data
public class PhotoVo {
     Integer id;
     Integer recipeId;
     String photoUrl;
     String extension;
     public Photo toModel(Recipe recipe){
          Photo photo = new Photo();
          photo.setRecipe(recipe);
          photo.setPhotoUrl(getPhotoUrl());
          photo.setExtension(getExtension());
          photo.setId(getId());
          return photo;
     }
}
