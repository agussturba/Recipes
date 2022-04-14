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
    @ApiOperation(value = "Retrieve a list of instructions", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved a list of instructions"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
    })
    public ResponseEntity<List<InstructionVo>> getAllInstructions() {
        List<InstructionVo> result = transformListToVoList(instructionService.getAllInstructions());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Retrieve a instruction by its db id", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "Successfully retrieved the instruction"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The instruction was not found")
    })
    public ResponseEntity<InstructionVo> getInstructionById(@PathVariable Integer id) throws InstructionNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(instructionService.getInstructionById(id).toVO());
    }

    @GetMapping("/recipe/{recipeId}")
    @ApiOperation(value = "Retrieve a list of instruction by its recipe Id", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the instruction of the recipe"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The Recipe was not found")
    })
    public ResponseEntity<List<InstructionVo>> getInstructionsByRecipeId(@PathVariable Integer recipeId) throws RecipeNotFoundException {
        List<InstructionVo> result = transformListToVoList(instructionService.getInstructionsByRecipeId(recipeId));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping
    @ApiOperation(value = "Save a new instruction", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created a new instruction for a recipe"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The Recipe was not found")
    })
    public ResponseEntity<InstructionVo> saveInstruction(@RequestBody InstructionVo instructionVo) throws RecipeNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(instructionService.saveOrUpdateInstruction(instructionVo).toVO());
    }

    @PutMapping
    @ApiOperation(value = "Updated a instruction", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Updated an instruction for a recipe"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The Recipe or instruction was not found")
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
