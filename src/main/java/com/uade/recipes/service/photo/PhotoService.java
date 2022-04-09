package com.uade.recipes.service.photo;

import com.uade.recipes.model.Photo;
import com.uade.recipes.vo.PhotoVo;

import java.util.List;

public interface PhotoService {
    Photo getPhotoById(Integer id);
    List<Photo> getAllPhotos();
    List<Photo> getPhotosByRecipeId(Integer recipeId);
    Photo saveOrUpdatePhoto(PhotoVo photoVo);
}
