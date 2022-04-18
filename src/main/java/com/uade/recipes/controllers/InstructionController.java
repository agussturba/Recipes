package com.uade.recipes.controllers;

import com.uade.recipes.exceptions.instructionExceptions.InstructionNotFoundException;
import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.model.IngredientQuantity;
import com.uade.recipes.model.Instruction;
import com.uade.recipes.service.instruction.InstructionService;
import com.uade.recipes.vo.IngredientQuantityVo;
import com.uade.recipes.vo.InstructionVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/instruction")
public class InstructionController {
    private final InstructionService instructionService;

    public InstructionController(InstructionService instructionService) {
        this.instructionService = instructionService;
    }

    @GetMapping
    @ApiOperation(value = "Retornar una lista con todas las instrucciones", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista con todas las instrucciones retornada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
    })
    public ResponseEntity<List<InstructionVo>> getAllInstructions() {
        List<InstructionVo> result = transformListToVoList(instructionService.getAllInstructions());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Retornar una instruccion por su ID", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "Instruccion retornada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "La instruccion no fue encontrada")
    })
    public ResponseEntity<InstructionVo> getInstructionById(@PathVariable Integer id) throws InstructionNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(instructionService.getInstructionById(id).toVO());
    }

    @GetMapping("/recipe/{recipeId}")
    @ApiOperation(value = "Retornar todas las instrucciones pasado el ID de una receta", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "Instrucciones de la receta retornada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "La receta no fue encontrada")
    })
    public ResponseEntity<List<InstructionVo>> getInstructionsByRecipeId(@PathVariable Integer recipeId) throws RecipeNotFoundException {
        List<InstructionVo> result = transformListToVoList(instructionService.getInstructionsByRecipeId(recipeId));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping
    @ApiOperation(value = "Crear una nueva instruccion", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "Instrucciones de la receta creadas satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "La receta no fue encontrada")
    })
    public ResponseEntity<InstructionVo> saveInstruction(@RequestBody InstructionVo instructionVo) throws RecipeNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(instructionService.saveOrUpdateInstruction(instructionVo).toVO());
    }

    @PutMapping
    @ApiOperation(value = "Actualizar una instruccion", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "Instrucciones de la receta actualizadas satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "La receta o la instruccion no fue encontrada")
    })
    public ResponseEntity<InstructionVo> updateInstruction(@RequestBody InstructionVo instructionVo) throws RecipeNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(instructionService.saveOrUpdateInstruction(instructionVo).toVO());
    }

    private List<InstructionVo> transformListToVoList(List<Instruction> list){
        List<InstructionVo> result = new ArrayList<>();
        for(Instruction inst: list){
            result.add(inst.toVO());
        }
        return result;
    }
}
