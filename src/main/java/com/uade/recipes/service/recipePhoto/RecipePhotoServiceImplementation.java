package com.uade.recipes.service.recipePhoto;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.exceptions.recipePhotoExceptions.RecipePhotoNotFoundException;
import com.uade.recipes.model.Recipe;
import com.uade.recipes.model.RecipePhoto;
import com.uade.recipes.persistance.RecipePhotoRepository;
import com.uade.recipes.persistance.RecipeRepository;
import com.uade.recipes.utilities.CloudinaryUtil;
import com.uade.recipes.vo.RecipePhotoVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public List<RecipePhoto> getRecipePhotosByRecipeId(Integer recipeId) throws RecipeNotFoundException {
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(RecipeNotFoundException::new);
        return recipePhotoRepository.findByRecipe(recipe);
    }

    @Override
    public List<Object> saveRecipePhoto(Integer recipeId, List<MultipartFile> images) throws RecipeNotFoundException, IOException {
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(RecipeNotFoundException::new);
        List<RecipePhoto> recipePhotos = new ArrayList<>();
        if (recipe.getMainPhoto() == null) {
            Map uploadResult = saveRecipePhotoToCloudinary(recipeId, images.get(0));
            recipe.setMainPhoto(uploadResult.get("url").toString());
            images.remove(0);
        }
        for (MultipartFile image : images) {
            recipePhotos.add(setNewImageRecipe(image, recipe));
        }
        recipeRepository.save(recipe);
        List<Object> result = new ArrayList<>();
        result.add(recipe.getMainPhoto());
        result.addAll(transformListToVoList(recipePhotos));
        recipePhotoRepository.saveAll(recipePhotos);
        return result;
    }

    private List<RecipePhotoVo> transformListToVoList(List<RecipePhoto> list){
        return list.stream().map(RecipePhoto::toVO).collect(Collectors.toList());
    }

    private RecipePhoto setNewImageRecipe(MultipartFile image, Recipe recipe) throws IOException {
        RecipePhoto recipePhoto = new RecipePhoto();
        recipePhoto.setRecipe(recipe);
        Map uploadResult = saveRecipePhotoToCloudinary(recipe.getId(), image);
        recipePhoto.setPhotoUrl(uploadResult.get("url").toString());
        recipePhoto.setExtension(StringUtils.getFilenameExtension(image.getOriginalFilename()));
        return recipePhoto;
    }


    @Override
    public void deleteRecipePhoto(Integer recipeId, Integer recipePhotoId) throws IOException {
        RecipePhoto recipePhoto = this.getRecipePhotoById(recipePhotoId);
        List<String> url = Arrays.asList(recipePhoto.getPhotoUrl().split("/"));
        String filename = url.get(url.size() - 1);
        String publicId = filename.substring(0, filename.indexOf("."));
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        recipePhotoRepository.delete(recipePhoto);
    }

    @Override
    public void deleteAllRecipePhotos(Integer recipeId) throws RecipeNotFoundException, IOException {
        List<RecipePhoto> photos = getRecipePhotosByRecipeId(recipeId);
        for (RecipePhoto rp : photos) {
            deleteRecipePhoto(recipeId, rp.getId());
        }
    }

    @Override
    public void deleteRecipePhotoFromDB(Integer recipePhotoId) throws RecipeNotFoundException {
        RecipePhoto rp = getRecipePhotoById(recipePhotoId);
        recipePhotoRepository.delete(rp);
    }

    public List<RecipePhoto> getRecipePhotosByIds(List<Integer> recipePhotoIdList) {
        return (List<RecipePhoto>) recipePhotoRepository.findAllById(recipePhotoIdList);
    }

    private Map saveRecipePhotoToCloudinary(Integer recipeId, MultipartFile image) throws IOException {
        return cloudinary.uploader().upload(image.getBytes(),
                ObjectUtils.emptyMap());
    }
    /**
     *   Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(RecipeNotFoundException::new);
     *         RecipePhoto recipePhoto;
     *         Map uploadResult;
     *         List<RecipePhoto> recipePhotos = new ArrayList<>();
     *         for (MultipartFile image : images) {
     *             recipePhoto = new RecipePhoto();
     *             recipePhoto.setRecipe(recipe);
     *             uploadResult = saveRecipePhotoToCloudinary(recipeId, image);
     *             recipePhoto.setPhotoUrl((String) uploadResult.get("url"));
     *             recipePhoto.setExtension(StringUtils.getFilenameExtension(image.getOriginalFilename()));
     *             recipePhotos.add(recipePhoto);
     *         }
     *         return (List<RecipePhoto>) recipePhotoRepository.saveAll(recipePhotos);
     *     }
     */
}

