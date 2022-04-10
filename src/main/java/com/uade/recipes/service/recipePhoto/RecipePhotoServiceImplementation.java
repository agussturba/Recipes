package com.uade.recipes.service.recipePhoto;

import com.uade.recipes.exceptions.recipePhotoExceptions.RecipePhotoNotFoundException;
import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.model.Recipe;
import com.uade.recipes.model.RecipePhoto;
import com.uade.recipes.persistance.RecipePhotoRepository;
import com.uade.recipes.persistance.RecipeRepository;
import com.uade.recipes.vo.RecipePhotoVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipePhotoServiceImplementation implements RecipePhotoService {
    private final RecipePhotoRepository recipePhotoRepository;
    private final RecipeRepository recipeRepository;

    public RecipePhotoServiceImplementation(RecipePhotoRepository recipePhotoRepository, RecipeRepository recipeRepository) {
        this.recipePhotoRepository = recipePhotoRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public RecipePhoto getRecipePhotoById(Integer id) {
        return recipePhotoRepository.findById(id).orElseThrow(RecipePhotoNotFoundException::new);
    }

    @Override
    public List<RecipePhoto> getAllRecipePhotos() {
        return (List<RecipePhoto>) recipePhotoRepository.findAll();
    }

    @Override
    public List<RecipePhoto> getRecipePhotosByRecipeId(Integer recipeId) throws RecipeNotFoundException {
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(RecipeNotFoundException::new);
        return recipePhotoRepository.findByRecipe(recipe);
    }

    @Override
    public RecipePhoto saveOrUpdateRecipePhoto(RecipePhotoVo recipePhotoVo) throws RecipeNotFoundException {
        Recipe recipe = recipeRepository.findById(recipePhotoVo.getRecipeId()).orElseThrow(RecipeNotFoundException::new);
        return recipePhotoRepository.save(recipePhotoVo.toModel(recipe));
    }
}
