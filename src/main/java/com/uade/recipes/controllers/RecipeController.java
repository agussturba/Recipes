package com.uade.recipes.controllers;

import com.uade.recipes.exceptions.dishExceptions.DishNotFoundException;
import com.uade.recipes.exceptions.ingredientExceptions.CannotDivideTheIngredientException;
import com.uade.recipes.exceptions.ingredientExceptions.IngredientNotFoundException;
import com.uade.recipes.exceptions.instructionExceptions.InstructionNotFoundException;
import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.exceptions.userExceptions.UserNotFoundException;
import com.uade.recipes.model.IngredientQuantity;
import com.uade.recipes.model.Recipe;
import com.uade.recipes.service.ingredientQuantity.IngredientQuantityService;
import com.uade.recipes.service.recipe.RecipeService;
import com.uade.recipes.vo.IngredientQuantityVo;
import com.uade.recipes.vo.RecipeVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.models.auth.In;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/recipes")
public class RecipeController {
    private final RecipeService recipeService;
    private final IngredientQuantityService ingredientQuantityService;

    public RecipeController(RecipeService recipeService, IngredientQuantityService ingredientQuantityService) {
        this.recipeService = recipeService;
        this.ingredientQuantityService = ingredientQuantityService;
    }

    @GetMapping
    @ApiOperation(value = "Retornar una lista de todas las recetas", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de recetas retornada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "El plato/usuario no fue encontrado")
    })
    public ResponseEntity<List<RecipeVo>> getAllRecipes(@RequestParam(required = false) Integer userId, @RequestParam(required = false) Integer dishId, @RequestParam(required = false) Integer peopleAmount) throws DishNotFoundException, UserNotFoundException {
        if (userId == null && dishId == null && peopleAmount == null) {
            List<RecipeVo> result = transformListToVoList(recipeService.getAllRecipes());
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else if (userId != null && dishId == null && peopleAmount == null) {
            List<RecipeVo> result = transformListToVoList(recipeService.getRecipesByUserId(userId));
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else if (userId == null && dishId != null && peopleAmount == null) {
            List<RecipeVo> result = transformListToVoList(recipeService.getRecipesByDishId(dishId));
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else if (userId != null && dishId != null && peopleAmount == null) {
            List<RecipeVo> result = transformListToVoList(recipeService.getRecipesByUserIdAndDishId(userId, dishId));
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else if (userId != null && dishId == null && peopleAmount != null) {
            List<RecipeVo> result = transformListToVoList(recipeService.getRecipesByUserIdAndPeopleAmount(userId, peopleAmount));
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else if (userId == null && dishId == null && peopleAmount != null) {
            List<RecipeVo> result = transformListToVoList(recipeService.getRecipesByPeopleAmount(peopleAmount));
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else if (userId == null && dishId != null && peopleAmount != null) {
            List<RecipeVo> result = transformListToVoList(recipeService.getRecipesByDishIdAndPeopleAmount(dishId, peopleAmount));
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        List<RecipeVo> result = transformListToVoList(recipeService.getRecipesByUserIdAndDishIdAndPeopleAmount(userId, dishId, peopleAmount));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    @GetMapping("/name/{name}")
    @ApiOperation(value = "Retornar recetas por nombre", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de recetas por su nombre retornada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "No existen recetas con dicho nombre")

    })
    public ResponseEntity<List<RecipeVo>> getRecipesByName(@PathVariable String name) {
        List<RecipeVo> result = transformListToVoList(recipeService.getRecipesByName(name));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    @GetMapping("/name/{ingredientId}")
    @ApiOperation(value = "Retornar recetas por un ingrediente faltante", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de recetas por ingrediente faltante retornada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "No existen ingrediente con dicho id")

    })
    public ResponseEntity<List<RecipeVo>> getRecipesByMissingIngredientId(@PathVariable Integer ingredientId) throws IngredientNotFoundException {
        List<RecipeVo> result = transformListToVoList(recipeService.getRecipesByMissingIngredientId(ingredientId));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    @GetMapping("/enabled/{recipeId}")
    @ApiOperation(value = "Verificar si una receta esta habilitada", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La receta esta habilitada o deshabilitada"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "No existen dicha receta")

    })
    public ResponseEntity<Boolean> isRecipeEnabled(@PathVariable Integer recipeId) throws UserNotFoundException, RecipeNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(recipeService.isRecipeEnabled(recipeId));
    }

    @GetMapping("/type")
    @ApiOperation(value = "Retornar recetas por su tipo", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de recetas por su tipo retornada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "No existen recetas con dicho tipo")

    })
    public ResponseEntity<List<RecipeVo>> getRecipesByTypes(@RequestBody List<Integer> typesIds) {//TODO  PUEDE SER UN REQUEST PARAM
        List<RecipeVo> result = transformListToVoList(recipeService.getRecipesByTypes(typesIds));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    @GetMapping("/convert")
    @ApiOperation(value = "Convertir una receta por un factor de conversion", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de recetas por su tipo retornada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "No se pudo dividir un ingrediente")

    })
    public ResponseEntity<List<IngredientQuantityVo>> convertRecipeIngredientQuantityByConversionFactor(@RequestParam Integer recipeId,@RequestParam Double conversionFactor) throws IngredientNotFoundException, RecipeNotFoundException, CannotDivideTheIngredientException {
        List<IngredientQuantityVo> result = transformIngredientQuantityListToVoList(recipeService.convertRecipeIngredientQuantityByConversionFactor(recipeId,conversionFactor));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    @GetMapping("/convert/ingredient")
    @ApiOperation(value = "Convertir una receta apartir de una nueva cantidad de ingrediente", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de recetas por su tipo retornada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "No se pudo dividir un ingrediente")

    })
    public ResponseEntity<List<IngredientQuantityVo>> convertRecipeIngredientQuantityByRecipeIdAndIngredientIdAndNewQuantity(@RequestParam Integer recipeId,@RequestParam Integer ingredientId,@RequestParam Double quantity) throws IngredientNotFoundException, RecipeNotFoundException, CannotDivideTheIngredientException {
        List<IngredientQuantityVo> result = transformIngredientQuantityListToVoList(recipeService.convertRecipeIngredientQuantityByIngredientIdAndRecipeIdAndNewQuantity(ingredientId,quantity,recipeId));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/ingredients")
    public ResponseEntity<List<RecipeVo>> getRecipesByIngredient(@RequestParam Integer ingredientId) throws IngredientNotFoundException {
        List<Recipe> result = getRecipesFromIngredientQuantity(ingredientQuantityService.getIngredientQuantityByIngredientId(ingredientId));
        List<RecipeVo> resultFinal = transformListToVoList(result);
        return ResponseEntity.status(HttpStatus.FOUND).body(resultFinal);
    }

    @PostMapping
    @ApiOperation(value = "Crear una nueva receta", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Receta creada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "El usuario/plato/tipo/foto de receta no fueron encontrados"),

    })
    public ResponseEntity<RecipeVo> saveRecipe(@RequestBody RecipeVo recipeVo) throws InstructionNotFoundException, DishNotFoundException, UserNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeService.saveOrUpdateRecipe(recipeVo).toVO());
    }


    @PutMapping
    @ApiOperation(value = "Actualizar una receta", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Receta actualizada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "El usuario/plato/tipo/foto de receta no fueron encontrados"),

    })
    public ResponseEntity<RecipeVo> updateRecipe(@RequestBody RecipeVo recipeVo) throws InstructionNotFoundException, DishNotFoundException, UserNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeService.saveOrUpdateRecipe(recipeVo).toVO());
    }

    private List<Recipe> getRecipesFromIngredientQuantity(List<IngredientQuantity> list) {
        List<Recipe> resultList = new ArrayList<>();
        for (IngredientQuantity ingredient : list) {
            resultList.add(ingredient.getRecipe());
        }
        return resultList;
    }

    private List<RecipeVo> transformListToVoList(List<Recipe> list) {
        List<RecipeVo> result = new ArrayList<>();
        for (Recipe obj : list) {
            result.add(obj.toVO());
        }
        return result;
    }

    private List<IngredientQuantityVo> transformIngredientQuantityListToVoList(List<IngredientQuantity> ingredientQuantityList) {
        List<IngredientQuantityVo> ingredientQuantityVoList = new ArrayList<>();
        for (IngredientQuantity ingredientQuantity : ingredientQuantityList) {
            ingredientQuantityVoList.add(ingredientQuantity.toVO());
        }
        return ingredientQuantityVoList;
    }

}
