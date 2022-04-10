package com.uade.recipes.service.recipePhoto;

import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.model.RecipePhoto;
import com.uade.recipes.vo.RecipePhotoVo;

import java.util.List;

public interface RecipePhotoService {
    RecipePhoto getRecipePhotoById(Integer id);

    List<RecipePhoto> getAllRecipePhotos();

    List<RecipePhoto> getRecipePhotosByRecipeId(Integer recipeId) throws RecipeNotFoundException;

    RecipePhoto saveOrUpdateRecipePhoto(RecipePhotoVo recipePhotoVo) throws RecipeNotFoundException;
}


