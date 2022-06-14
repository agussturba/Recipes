package com.uade.recipes.controllers;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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
    @ApiOperation(value = "Retornar una lista de todas las recetas donde se le puede pasar opcionalmente un user Id y/o la cantidad de personas", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de recetas retornada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "El usuario no fue encontrado")
    })
    public ResponseEntity<List<RecipeVo>> getAllRecipes(@RequestParam(required = false) Integer ownerId, @RequestParam(required = false) Integer peopleAmount) throws  UserNotFoundException {
        if (ownerId == null  && peopleAmount == null) {
            List<RecipeVo> result = transformListToVoList(recipeService.getAllRecipes());
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else if (ownerId != null  && peopleAmount == null) {
            List<RecipeVo> result = transformListToVoList(recipeService.getRecipesByOwnerId(ownerId));
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }  else if (ownerId != null  && peopleAmount != null) {
            List<RecipeVo> result = transformListToVoList(recipeService.getRecipesByOwnerIdAndPeopleAmount(ownerId, peopleAmount));
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else  {
            List<RecipeVo> result = transformListToVoList(recipeService.getRecipesByPeopleAmount(peopleAmount));
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }

    }

    @GetMapping("/{recipeId}")
    @ApiOperation(value = "Retornar una receta por su id", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "receta retornada correctamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "La receta no fue encontrada")
    })
    public ResponseEntity<RecipeVo> getRecipeById(@PathVariable Integer recipeId) throws RecipeNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(recipeService.getRecipeById(recipeId).toVO());
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
    public ResponseEntity<List<RecipeVo>> getRecipesByMissingIngredientId(@RequestBody List<Integer> ingredientIdList) throws IngredientNotFoundException {
        List<RecipeVo> result = transformListToVoList(recipeService.getRecipesByMissingIngredientId(ingredientIdList.get(0)));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/enabled/{recipeId}")
    @ApiOperation(value = "Verificar si una receta esta habilitada", response = Boolean.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La receta esta habilitada o deshabilitada"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "No existen dicha receta")

    })
    public ResponseEntity<Boolean> isRecipeEnabled(@PathVariable Integer recipeId) throws UserNotFoundException, RecipeNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(recipeService.isRecipeEnabled(recipeId));
    }

    @PutMapping("/enabled/{recipeId}")
    @ApiOperation(value = "Habilitar una receta", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La receta esta habilitada"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "No existen dicha receta")

    })
    public ResponseEntity<RecipeVo> enabledRecipeByRecipeId(@PathVariable Integer recipeId) {
        return ResponseEntity.status(HttpStatus.OK).body(new RecipeVo());
    }

    @GetMapping("/type")
    @ApiOperation(value = "Obtener una lista de recetas a partir un grupo de tipos", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de recetas por su tipo retornada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "No existen recetas con dicho tipo")

    })
    public ResponseEntity<List<RecipeVo>> getRecipesByTypes(@RequestBody List<Integer> typesIds) {
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
    public ResponseEntity<List<IngredientQuantityVo>> convertRecipeIngredientQuantityByConversionFactor(@RequestParam Integer recipeId, @RequestParam Double conversionFactor) throws IngredientNotFoundException, RecipeNotFoundException, CannotDivideTheIngredientException {
        List<IngredientQuantityVo> result = transformIngredientQuantityListToVoList(recipeService.convertRecipeIngredientQuantityByConversionFactor(recipeId, conversionFactor));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/convert/ingredient")
    @ApiOperation(value = "Convertir una receta a partir de una nueva cantidad de ingrediente", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de recetas por su tipo retornada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "No se pudo dividir un ingrediente")

    })
    public ResponseEntity<List<IngredientQuantityVo>> convertRecipeIngredientQuantityByRecipeIdAndIngredientIdAndNewQuantity(@RequestParam Integer recipeId, @RequestParam Integer ingredientId, @RequestParam Double quantity) throws IngredientNotFoundException, RecipeNotFoundException, CannotDivideTheIngredientException {
        List<IngredientQuantityVo> result = transformIngredientQuantityListToVoList(recipeService.convertRecipeIngredientQuantityByIngredientIdAndRecipeIdAndNewQuantity(ingredientId, quantity, recipeId));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/ingredients")
    @ApiOperation(value = "Obtener una lista de recetas a partir del id de un ingredient", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de recetas por un ingrediente retornada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "No se pudo encontrar el ingrediente o recetas")

    })
    public ResponseEntity<List<RecipeVo>> getRecipesByIngredient(@RequestBody List<Integer> ingredientIdList) throws IngredientNotFoundException {
        List<Recipe> result = getRecipesFromIngredientQuantity(ingredientQuantityService.getIngredientQuantityByIngredientId(ingredientIdList.get(0)));
        List<RecipeVo> resultFinal = transformListToVoList(result);
        return ResponseEntity.status(HttpStatus.OK).body(resultFinal);
    }

    @PostMapping
    @ApiOperation(value = "Crear una nueva receta", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Receta creada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "El usuario/plato/tipo/foto de receta no fueron encontrados"),

    })
    public ResponseEntity<RecipeVo> saveRecipe(@RequestBody RecipeVo recipeVo) throws InstructionNotFoundException, UserNotFoundException {
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
    public ResponseEntity<RecipeVo> updateRecipe(@RequestBody RecipeVo recipeVo) throws InstructionNotFoundException, UserNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeService.saveOrUpdateRecipe(recipeVo).toVO());
    }

    private List<Recipe> getRecipesFromIngredientQuantity(List<IngredientQuantity> list) {
        return list.stream().map(IngredientQuantity::getRecipe).collect(Collectors.toList());
    }

    private List<RecipeVo> transformListToVoList(List<Recipe> list) {
        return list.stream().map(Recipe::toVO).collect(Collectors.toList());

    }

    private List<IngredientQuantityVo> transformIngredientQuantityListToVoList(List<IngredientQuantity> ingredientQuantityList) {
        return ingredientQuantityList.stream().map(IngredientQuantity::toVO).collect(Collectors.toList());
    }

}
