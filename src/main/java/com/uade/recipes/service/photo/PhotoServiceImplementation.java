package com.uade.recipes.service.photo;

import com.uade.recipes.exceptions.PhotoNotFoundException;
import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.model.Photo;
import com.uade.recipes.model.Recipe;
import com.uade.recipes.persistance.PhotoRepository;
import com.uade.recipes.persistance.RecipeRepository;
import com.uade.recipes.vo.PhotoVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotoServiceImplementation implements PhotoService {
    private final PhotoRepository photoRepository;
    private final RecipeRepository recipeRepository;

    public PhotoServiceImplementation(PhotoRepository photoRepository, RecipeRepository recipeRepository) {
        this.photoRepository = photoRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Photo getPhotoById(Integer id) {
        return photoRepository.findById(id).orElseThrow(PhotoNotFoundException::new);
    }

    @Override
    public List<Photo> getAllPhotos() {
        return (List<Photo>) photoRepository.findAll();
    }

    @Override
    public List<Photo> getPhotosByRecipeId(Integer recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(RecipeNotFoundException::new);
        return photoRepository.findByRecipe(recipe);
    }

    @Override
    public Photo saveOrUpdatePhoto(PhotoVo photoVo) {
        Recipe recipe = recipeRepository.findById(photoVo.getRecipeId()).orElseThrow(RecipeNotFoundException::new);
        return photoRepository.save(photoVo.toModel(recipe));
    }
}
