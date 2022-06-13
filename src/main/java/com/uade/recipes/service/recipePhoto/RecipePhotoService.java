package com.uade.recipes.service.recipePhoto;

import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.model.RecipePhoto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RecipePhotoService {
    RecipePhoto getRecipePhotoById(Integer id);

    List<RecipePhoto> getRecipePhotosByRecipeId(Integer recipeId) throws RecipeNotFoundException;

    Iterable<Object> saveRecipePhoto(Integer recipeId, List<MultipartFile> images) throws RecipeNotFoundException, IOException;

    void deleteRecipePhoto(Integer recipeId, Integer recipePhotoId) throws RecipeNotFoundException, IOException;


}


