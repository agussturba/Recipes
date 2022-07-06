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
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
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
    public ResponseEntity<List<RecipeVo>> getAllRecipes(@RequestParam(required = false) Integer ownerId, @RequestParam(required = false) Integer peopleAmount) throws UserNotFoundException {
        if (ownerId == null && peopleAmount == null) {
            List<RecipeVo> result = transformListToVoList(recipeService.getAllRecipes());
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else if (ownerId != null && peopleAmount == null) {
            List<RecipeVo> result = transformListToVoList(recipeService.getRecipesByOwnerId(ownerId));
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else if (ownerId != null && peopleAmount != null) {
            List<RecipeVo> result = transformListToVoList(recipeService.getRecipesByOwnerIdAndPeopleAmount(ownerId, peopleAmount));
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else {
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

    @GetMapping("/filter")
    @ApiOperation(value = "Retornar recetas por tipos,una lista de ingredientes para ser incluidos y otra para ser excluidos", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de recetas retornada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "Tipo no encontrado o ingrediente no encontrado")

    })
    public ResponseEntity<List<RecipeVo>> getRecipesByIncludedIngredientsAndExcludedIngredientsAndTypes(@RequestParam List<Integer> typesIds, @RequestParam List<Integer> includedIngredients, @RequestParam List<Integer> excludedIngredients) throws IngredientNotFoundException {
        if (typesIds.size() == 0 && includedIngredients.size() == 0 && excludedIngredients.size() == 0) {
            return ResponseEntity.status(HttpStatus.OK).body(transformListToVoList(recipeService.getAllRecipes()));
        } else if (typesIds.size() != 0 && includedIngredients.size() == 0 && excludedIngredients.size() == 0) {
            return ResponseEntity.status(HttpStatus.OK).body(transformListToVoList(recipeService.getRecipesByTypes(typesIds)));
        } else if (typesIds.size() == 0 && includedIngredients.size() != 0 && excludedIngredients.size() == 0) {
            List<Recipe> recipes = new ArrayList(recipeService.getRecipesByIngredients(includedIngredients));
            return ResponseEntity.status(HttpStatus.OK).body(transformListToVoList(recipes));
        } else if (typesIds.size() == 0 && includedIngredients.size() == 0 && excludedIngredients.size() != 0) {
            List<Recipe> recipes = new ArrayList(recipeService.getRecipesByMissingIngredientIdList(excludedIngredients));
            return ResponseEntity.status(HttpStatus.OK).body(transformListToVoList(recipes));
        } else if (typesIds.size() != 0 && includedIngredients.size() != 0 && excludedIngredients.size() == 0) {
            List<Recipe> recipes = new ArrayList(recipeService.getRecipesByTypesAndIngredients(typesIds, includedIngredients));
            return ResponseEntity.status(HttpStatus.OK).body(transformListToVoList(recipes));
        } else if (typesIds.size() != 0 && includedIngredients.size() == 0 && excludedIngredients.size() != 0) {
            List<Recipe> recipes = new ArrayList(recipeService.getRecipesByTypesAndExcludedIngredients(typesIds, excludedIngredients));
            return ResponseEntity.status(HttpStatus.OK).body(transformListToVoList(recipes));
        } else if (typesIds.size() == 0 && includedIngredients.size() != 0 && excludedIngredients.size() != 0) {
            List<Recipe> recipes = new ArrayList(recipeService.getRecipesByIncludedIngredientsAndExcludedIngredients(includedIngredients, excludedIngredients));
            return ResponseEntity.status(HttpStatus.OK).body(transformListToVoList(recipes));
        } else {
            List<Recipe> recipes = new ArrayList(recipeService.getRecipesByIncludedIngredientsAndExcludedIngredientsAndTypes(includedIngredients, excludedIngredients, typesIds));
            return ResponseEntity.status(HttpStatus.OK).body(transformListToVoList(recipes));
        }

    }


    @GetMapping("/name")
    @ApiOperation(value = "Retornar recetas por nombre", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de recetas por su nombre retornada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "No existen recetas con dicho nombre")

    })
    public ResponseEntity<List<RecipeVo>> getRecipesByName(@RequestParam String name) {
        List<RecipeVo> result = transformListToVoList(recipeService.getRecipesByName(name));
        return ResponseEntity.status(HttpStatus.OK).body(result);
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

    @GetMapping("/count/{ownerId}")
    @ApiOperation(value = "Obtener la cantidad de recetas de un usuario", response = Integer.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cantidad retornada con exito"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "No existen recetas de dicho usuario o no existe el usuario")

    })
    public ResponseEntity<Integer> getAmountOfRecipesByOwnerId(@PathVariable Integer ownerId) throws UserNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(recipeService.getAmountOfRecipesByOwnerId(ownerId));
    }

    @GetMapping("/convert")
    @ApiOperation(value = "Convertir una receta por un factor de conversion", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de recetas por su tipo retornada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),

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
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeService.saveRecipe(recipeVo).toVO());
    }


    @PutMapping
    @ApiOperation(value = "Actualizar una receta", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Receta actualizada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "El usuario/plato/tipo/foto de receta no fueron encontrados"),

    })
    public ResponseEntity<RecipeVo> updateRecipe(@RequestBody RecipeVo recipeVo) throws RecipeNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeService.updateRecipe(recipeVo).toVO());
    }


    private List<Recipe> getRecipesFromIngredientQuantity(List<IngredientQuantity> list) {
        return list.stream().map(IngredientQuantity::getRecipe).collect(Collectors.toList());
    }

    @GetMapping("/like")
    @ApiOperation(value = "Devuelve las recetas cuyo nombre es similar al pasado como parametro", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Se encontraron recetas"),
            @ApiResponse(code = 404, message = "No se encontraron recetas"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
    })
    public ResponseEntity<List<RecipeVo>> getRecipesFromPartialName(@RequestParam String name) throws RecipeNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(transformListToVoList(recipeService.findRecipesByPartialName(name)));
    }

    @GetMapping("/username/like")
    @ApiOperation(value = "Devuelve las recetas creadas por un usuario cuyo nombre de usuario es similar al pasado como parámetro", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Se encontraron recetas"),
            @ApiResponse(code = 404, message = "No se encontraron recetas"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
    })
    public ResponseEntity<List<RecipeVo>> getRecipesByPartialUsername(@RequestParam String username) throws UserNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(transformListToVoList(recipeService.findRecipesByPartialUsername(username)));
    }

    @DeleteMapping("/mainphoto/{recipeId}")
    @ApiOperation(value = "Elimina la foto principal de una receta", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La foto fue eliminada con éxito"),
            @ApiResponse(code = 404, message = "No se encontraron recetas"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
    })

    public ResponseEntity deleteMainPhoto(@PathVariable Integer recipeId) throws RecipeNotFoundException, IOException {
        recipeService.deleteMainPhoto(recipeId);
        return new ResponseEntity(HttpStatus.OK);
    }

    private List<RecipeVo> transformListToVoList(List<Recipe> list) {
        return list.stream().filter(Recipe::isEnabled).map(Recipe::toVO).collect(Collectors.toList());

    }

    private List<IngredientQuantityVo> transformIngredientQuantityListToVoList(List<IngredientQuantity> ingredientQuantityList) {
        return ingredientQuantityList.stream().map(IngredientQuantity::toVO).collect(Collectors.toList());
    }

}
