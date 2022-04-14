package com.uade.recipes.service.recipePhoto;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.uade.recipes.exceptions.recipePhotoExceptions.RecipePhotoNotFoundException;
import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.model.Recipe;
import com.uade.recipes.model.RecipePhoto;
import com.uade.recipes.persistance.RecipePhotoRepository;
import com.uade.recipes.persistance.RecipeRepository;
import com.uade.recipes.utilities.CloudinaryUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class RecipePhotoServiceImplementation implements RecipePhotoService {
    private final RecipePhotoRepository recipePhotoRepository;
    private final RecipeRepository recipeRepository;
    private final Cloudinary cloudinary = CloudinaryUtil.getInstance();

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
    public List<RecipePhoto> saveRecipePhoto(Integer recipeId, List<MultipartFile> images) throws RecipeNotFoundException, IOException {
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(RecipeNotFoundException::new);
        RecipePhoto recipePhoto;
        Map uploadResult;
        List<RecipePhoto> recipePhotos = new ArrayList<>();
        for (MultipartFile image : images) {
            recipePhoto = new RecipePhoto();
            recipePhoto.setRecipe(recipe);
            uploadResult = saveRecipePhotoToCloudinary(recipeId, image);
            recipePhoto.setPhotoUrl((String) uploadResult.get("url"));
            recipePhoto.setExtension(StringUtils.getFilenameExtension(image.getOriginalFilename()));
            recipePhotos.add(recipePhoto);
        }
        return (List<RecipePhoto>) recipePhotoRepository.saveAll(recipePhotos);
    }

    @Override
    public void deleteRecipePhoto(Integer recipeId, Integer recipePhotoId) throws RecipeNotFoundException, IOException {
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(RecipeNotFoundException::new);
        RecipePhoto recipePhoto = this.getRecipePhotoById(recipePhotoId);
        List<String> url = Arrays.asList(recipePhoto.getPhotoUrl().split("/"));
        String filename = url.get(url.size()-1);
        String public_id = filename.substring(0, filename.indexOf("."));
        cloudinary.uploader().destroy(public_id, ObjectUtils.emptyMap());
        recipePhotoRepository.delete(recipePhoto);
    }

    @Override
    public List<RecipePhoto> getRecipePhotosByIds(List<Integer> recipePhotoIdList) {//TODO TEST
        return (List<RecipePhoto>) recipePhotoRepository.findAllById(recipePhotoIdList);
    }

    private Map saveRecipePhotoToCloudinary(Integer recipeId, MultipartFile image) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(image.getBytes(),
                ObjectUtils.emptyMap());
        return uploadResult;
    }
}
