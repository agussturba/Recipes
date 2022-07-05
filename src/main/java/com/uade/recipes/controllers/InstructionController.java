package com.uade.recipes.controllers;

import com.uade.recipes.exceptions.instructionExceptions.InstructionNotFoundException;
import com.uade.recipes.exceptions.recipeExceptions.RecipeNotFoundException;
import com.uade.recipes.model.Instruction;
import com.uade.recipes.service.instruction.InstructionService;
import com.uade.recipes.vo.InstructionVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/instruction")
public class InstructionController {
    private final InstructionService instructionService;

    public InstructionController(InstructionService instructionService) {
        this.instructionService = instructionService;
    }


    @GetMapping("/{id}")
    @ApiOperation(value = "Retornar una instrucción por su ID", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "Instrucción retornada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "La instrucción no fue encontrada")
    })
    public ResponseEntity<InstructionVo> getInstructionById(@PathVariable Integer id) throws InstructionNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(instructionService.getInstructionById(id).toVO());
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
    @ApiOperation(value = "Crear una nueva instrucción", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Instrucción creada correctamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "La receta no fue encontrada")
    })
    public ResponseEntity<InstructionVo> saveInstruction(@RequestBody InstructionVo instructionVo) throws RecipeNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(instructionService.saveOrUpdateInstruction(instructionVo).toVO());
    }

    @PutMapping
    @ApiOperation(value = "Actualizar una instrucción", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Instrucciones de la receta actualizadas satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "La receta o la instrucción no fue encontrada")
    })
    public ResponseEntity<InstructionVo> updateInstruction(@RequestBody InstructionVo instructionVo) throws RecipeNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(instructionService.saveOrUpdateInstruction(instructionVo).toVO());
    }

    @DeleteMapping
    @ApiOperation(value = "Eliminar las instrucciones de una receta")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Instrucciones de la receta eliminadas satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "La receta o la instrucción no fue encontrada")
    })
    public ResponseEntity deleteAllInstructions(@RequestParam Integer recipeId) throws RecipeNotFoundException {
        instructionService.deleteAllInstructionsByRecipeId(recipeId);
        return new ResponseEntity(HttpStatus.OK);
    }

    private List<InstructionVo> transformListToVoList(List<Instruction> list){
        return list.stream().map(Instruction::toVO).collect(Collectors.toList());
    }
}
