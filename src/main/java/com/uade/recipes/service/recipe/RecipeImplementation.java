package com.uade.recipes.service.recipe;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.uade.recipes.exceptions.ingredientExceptions.CannotDivideTheIngredientException;
import com.uade.recipes.exceptions.ingredientExceptions.IngredientNotFoundException;
import com.uade.recipes.exceptions.instructionExceptions.InstructionNotFoundException;
import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;
import com.uade.recipes.model.*;
import com.uade.recipes.persistance.InstructionRepository;
import com.uade.recipes.persistance.MultimediaRepository;
import com.uade.recipes.persistance.RecipeRepository;
import com.uade.recipes.service.email.EmailSenderService;
import com.uade.recipes.service.ingredientQuantity.IngredientQuantityService;
import com.uade.recipes.service.recipePhoto.RecipePhotoService;
import com.uade.recipes.service.type.TypeService;
import com.uade.recipes.service.user.UserService;
import com.uade.recipes.utilities.CloudinaryUtil;
import com.uade.recipes.vo.RecipeVo;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import static com.uade.recipes.utilities.SetsUtilities.intersectionSet;

@Service
public class RecipeImplementation implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final UserService userService;
    private final TypeService typeService;
    private final IngredientQuantityService ingredientQuantityService;
    private final RecipePhotoService recipePhotoService;
    private final Cloudinary cloudinary = CloudinaryUtil.getInstance();
    private final EmailSenderService emailSenderService;
    private final InstructionRepository instructionRepository;
    private final MultimediaRepository multimediaRepository;

    public RecipeImplementation(RecipeRepository recipeRepository, UserService userService, TypeService typeService, IngredientQuantityService ingredientQuantityService, RecipePhotoService recipePhotoService, EmailSenderService emailSenderService, InstructionRepository instructionRepository, MultimediaRepository multimediaRepository) {
        this.recipeRepository = recipeRepository;
        this.userService = userService;
        this.typeService = typeService;
        this.ingredientQuantityService = ingredientQuantityService;
        this.recipePhotoService = recipePhotoService;
        this.emailSenderService = emailSenderService;
        this.instructionRepository = instructionRepository;
        this.multimediaRepository = multimediaRepository;
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return (List<Recipe>) recipeRepository.findAll();
    }

    @Override
    public Recipe getRecipeById(Integer recipeId) throws RecipeNotFoundException {
        return recipeRepository.findById(recipeId).orElseThrow(RecipeNotFoundException::new);
    }

    @Override
    public List<Recipe> getRecipesByName(String recipeName) {
        return recipeRepository.findByNameOrderByName(recipeName);
    }

    @Override
    public List<Recipe> getRecipesByPeopleAmount(Integer peopleAmount) {
        return recipeRepository.findByPeopleAmount(peopleAmount);
    }

    @Override
    public List<Recipe> getRecipesByOwnerId(Integer ownerId) throws UserNotFoundException {
        User owner = userService.getUserById(ownerId);
        return recipeRepository.findByOwnerOrderByName(owner);
    }

    @Override
    public List<Recipe> getRecipesByTypes(List<Integer> typesIds) {
        List<Type> type = typeService.getTypesByIdList(typesIds);
        return recipeRepository.findByTypeInOrderByName(type);
    }

    @Override
    public Set<Recipe> getRecipesByTypesAndIngredients(List<Integer> typesIds, List<Integer> ingredientsIds) throws IngredientNotFoundException {
        Set<Recipe> typesRecipe = new HashSet<>(this.getRecipesByTypes(typesIds));
        Set<Recipe> ingredientsRecipe = this.getRecipesByIngredients(ingredientsIds);
        return intersectionSet(typesRecipe, ingredientsRecipe);
    }

    @Override
    public Set<Recipe> getRecipesByTypesAndExcludedIngredients(List<Integer> typesIds, List<Integer> ingredientsIds) throws IngredientNotFoundException {
        Set<Recipe> typesRecipe = new HashSet<>(this.getRecipesByTypes(typesIds));
        Set<Recipe> ingredientsRecipe = this.getRecipesByMissingIngredientIdList(ingredientsIds);
        return intersectionSet(typesRecipe, ingredientsRecipe);
    }

    @Override
    public Set<Recipe> getRecipesByIngredients(List<Integer> ingredientsIds) throws IngredientNotFoundException {
        Set<Recipe> recipeSet = new HashSet<>();
        for (Integer ingredientId : ingredientsIds) {
            if (recipeSet.isEmpty()) {
                recipeSet.addAll(ingredientQuantityService.getRecipesByIngredientId(ingredientId));
            } else {
                recipeSet = intersectionSet(recipeSet, ingredientQuantityService.getRecipesByIngredientId(ingredientId));
            }
        }
        return recipeSet;
    }


    @Override
    public Set<Recipe> getRecipesByMissingIngredientIdList(List<Integer> ingredientIds) throws IngredientNotFoundException {
        Set<Recipe> recipeSet = new HashSet<>(getAllRecipes());
        for (Integer ingredientId : ingredientIds) {
            recipeSet = filterRecipesByIngredientId(recipeSet, ingredientId);
        }
        return recipeSet;
    }

    private Set<Recipe> filterRecipesByIngredientId(Set<Recipe> unFilterRecipes, Integer ingredientId) throws IngredientNotFoundException {
        Set<Recipe> recipes = ingredientQuantityService.getRecipesByIngredientId(ingredientId);
        unFilterRecipes.removeAll(recipes);
        return unFilterRecipes;
    }

    @Override
    public Set<Recipe> getRecipesByIncludedIngredientsAndExcludedIngredients(List<Integer> includedIngredientsIds, List<Integer> excludedIngredientsIds) throws IngredientNotFoundException {
        Set<Recipe> recipesByIngredients = this.getRecipesByIngredients(includedIngredientsIds);
        for (Integer excludedIngredientId : excludedIngredientsIds) {
            filterRecipesByIngredientId(recipesByIngredients, excludedIngredientId);
        }
        return recipesByIngredients;
    }

    @Override
    public Set<Recipe> getRecipesByIncludedIngredientsAndExcludedIngredientsAndTypes(List<Integer> includedIngredientsIds, List<Integer> excludedIngredientsIds, List<Integer> typesIds) throws IngredientNotFoundException {
        Set<Recipe> recipesByIngredients = this.getRecipesByIngredients(includedIngredientsIds);
        Set<Recipe> typesRecipe = new HashSet<>(this.getRecipesByTypes(typesIds));
        Set<Recipe> recipeSet = intersectionSet(recipesByIngredients, typesRecipe);
        for (Integer excludedIngredientId : excludedIngredientsIds) {
            filterRecipesByIngredientId(recipeSet, excludedIngredientId);
        }
        return recipeSet;
    }

    @Override
    public List<Recipe> getRecipesByOwnerIdAndPeopleAmount(Integer ownerId, Integer peopleAmount) throws UserNotFoundException {
        User owner = userService.getUserById(ownerId);
        return recipeRepository.findByOwnerAndPeopleAmount(owner, peopleAmount);
    }

    @Override
    public Integer getAmountOfRecipesByOwnerId(Integer ownerId) throws UserNotFoundException {
        userService.getUserById(ownerId);
        return this.getRecipesByOwnerId(ownerId).size();
    }

    @Override
    public void deleteRecipeByRecipeId(Integer recipeId) throws RecipeNotFoundException, InstructionNotFoundException, IOException {
        Recipe recipe = this.getRecipeById(recipeId);
        if (recipe.getMainPhoto() != null) {
            deleteRecipesPhotos(recipeId);
        }
        deleteInstructions(recipeId);
        ingredientQuantityService.deleteAllIngredientQuantities(recipeId);
        recipeRepository.delete(recipe);
    }

    @Override
    public void deleteMainPhoto(Integer recipeId) throws RecipeNotFoundException, IOException {
        Recipe recipe = getRecipeById(recipeId);
        List<String> url = Arrays.asList(recipe.getMainPhoto().split("/"));
        String filename = url.get(url.size() - 1);
        String publicId = filename.substring(0, filename.indexOf("."));
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        try {
            List<RecipePhoto> recipePhotoList = recipePhotoService.getRecipePhotosByRecipeId(recipeId);
            RecipePhoto recipePhoto = recipePhotoList.get(0);
            recipe.setMainPhoto(recipePhoto.getPhotoUrl());
            recipePhotoService.deleteRecipePhotoFromDB(recipePhoto.getId());
        } catch (Exception e) {
            recipe.setMainPhoto(null);
        }
        recipeRepository.save(recipe);
    }


    @Override
    public Recipe updateRecipe(RecipeVo recipeVo) throws RecipeNotFoundException {
        Recipe recipe = this.getRecipeById(recipeVo.getId());
        if (recipeVo.getTypeId() != null) {
            Type type = typeService.getTypeById(recipeVo.getTypeId());
            recipe.setType(type);
        }
        if (!recipeVo.getDescription().isEmpty()) {
            recipe.setDescription(recipeVo.getDescription());
        }
        if (!recipeVo.getName().isEmpty()) {
            recipe.setName(recipeVo.getName());
        }
        if (recipeVo.getPeopleAmount() != null) {
            recipe.setPeopleAmount(recipeVo.getPeopleAmount());
        }
        if (recipeVo.getPortions() != null) {
            recipe.setPortions(recipeVo.getPortions());
        }
        if (recipeVo.getDuration() != null) {
            recipe.setDuration(recipeVo.getDuration());
        }
        return recipeRepository.save(recipe);
    }

    public Recipe saveRecipe(RecipeVo recipeVo) throws UserNotFoundException {
        User owner = userService.getUserById(recipeVo.getOwnerId());
        Type type = typeService.getTypeById(recipeVo.getTypeId());
        recipeVo.setEnabled(false);
        recipeVo.setTimestamp(null);
        return recipeRepository.save(recipeVo.toModel(owner, type));
    }

    @Override
    public Recipe enabledRecipe(Integer recipeId) throws RecipeNotFoundException, UserNotFoundException {
        Recipe recipe = getRecipeById(recipeId);
        User owner = userService.getUserById(recipe.getOwnerId());
        recipe.setEnabled(true);
        recipe.setTimestamp(LocalDateTime.now());
        emailSenderService.sendSimpleEmail(owner.getEmail(), "Felicitaciones " + owner.getName() + "!\nTu receta \"" + recipe.getName() + "\" Ha sido aprobada.\nMuchas gracias por tu aporte a nuestra comunidad!", "Tu receta \"" + recipe.getName() + "\" ha sido aprobada");
        return recipeRepository.save(recipe);
    }

    @Override
    public List<IngredientQuantity> convertRecipeIngredientQuantityByIngredientIdAndRecipeIdAndNewQuantity(Integer ingredientId, Double newQuantity, Integer recipeId) throws IngredientNotFoundException, RecipeNotFoundException, CannotDivideTheIngredientException {
        IngredientQuantity ingredientQuantity = ingredientQuantityService.getIngredientQuantityByIngredientIdAndRecipeId(ingredientId, recipeId);
        Double conversionFactor = getConversionFactor(ingredientQuantity.getQuantity(), newQuantity);
        return ingredientQuantityService.getConvertedIngredientQuantityListByRecipeIdAndConversionFactor(recipeId, conversionFactor);
    }

    @Override
    public List<IngredientQuantity> convertRecipeIngredientQuantityByConversionFactor(Integer recipeId, Double conversionFactor) throws RecipeNotFoundException, CannotDivideTheIngredientException, IngredientNotFoundException {
        return ingredientQuantityService.getConvertedIngredientQuantityListByRecipeIdAndConversionFactor(recipeId, conversionFactor);
    }

    @Override
    public List<Recipe> findRecipesByPartialName(String name) throws RecipeNotFoundException {
        return recipeRepository.findByNameContainingIgnoreCase(name).orElseThrow(RecipeNotFoundException::new);
    }

    @Override
    public List<Recipe> findRecipesByPartialUsername(String username) throws UserNotFoundException {
        List<User> users = userService.getUsersByPartialUserName(username);
        List<Recipe> recipes = new ArrayList<>();
        for (User user : users) {
            recipes.addAll(getRecipesByOwnerId(user.getId()));
        }
        return recipes;
    }

    private List<Multimedia> getMultimediaByInstructionId(Integer instructionId) throws InstructionNotFoundException {
        Instruction instruction = instructionRepository.findById(instructionId).orElseThrow(InstructionNotFoundException::new);
        return multimediaRepository.findByInstruction(instruction);
    }

    private List<Instruction> getInstructionsByRecipeId(Integer recipeId) throws RecipeNotFoundException {
        Recipe recipe = this.getRecipeById(recipeId);
        return instructionRepository.findByRecipe(recipe);
    }

    private Double getConversionFactor(Double oldQuantity, Double newQuantity) {
        return newQuantity / oldQuantity;
    }

    private void deleteRecipesPhotos(Integer recipeId) throws RecipeNotFoundException, IOException {
        deleteMainPhoto(recipeId);
        List<RecipePhoto> recipePhotos = recipePhotoService.getRecipePhotosByRecipeId(recipeId);
        for (RecipePhoto recipePhoto :
                recipePhotos) {
            recipePhotoService.deleteRecipePhotoFromDB(recipePhoto.getId());
        }

    }

    private void deleteInstructions(Integer recipeId) throws RecipeNotFoundException, InstructionNotFoundException {
        List<Instruction> instructions = getInstructionsByRecipeId(recipeId);
        deleteMultimediaByListInstruction(instructions);
        instructionRepository.deleteAll(instructions);
    }

    private void deleteMultimediaByListInstruction(List<Instruction> instructionList) throws InstructionNotFoundException {
        for (Instruction instruction : instructionList) {
            List<Multimedia> multimediaList = this.getMultimediaByInstructionId(instruction.getId());
            multimediaRepository.deleteAll(multimediaList);
        }
    }

}
