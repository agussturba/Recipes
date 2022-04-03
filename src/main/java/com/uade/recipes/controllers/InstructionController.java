package com.uade.recipes.controllers;

import com.uade.recipes.model.Dish;
import com.uade.recipes.model.Instruction;
import com.uade.recipes.service.SequenceGeneratorService;
import com.uade.recipes.service.instruction.InstructionService;
import com.uade.recipes.vo.InstructionVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instruction")
public class InstructionController {
    private final InstructionService instructionService;
    private final SequenceGeneratorService sequenceGeneratorService;

    public InstructionController(InstructionService instructionService, SequenceGeneratorService sequenceGeneratorService) {
        this.instructionService = instructionService;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @GetMapping
    public ResponseEntity<List<Instruction>> getAllInstructions() {
        return ResponseEntity.status(HttpStatus.OK).body(instructionService.getAllInstructions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Instruction> getInstructionById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.FOUND).body(instructionService.getInstructionById(id));
    }

    @GetMapping("/recipe/{recipeId}")
    public ResponseEntity<List<Instruction>> getInstructionsByRecipeId(@PathVariable Integer recipeId) {
        return ResponseEntity.status(HttpStatus.OK).body(instructionService.getInstructionsByRecipeId(recipeId));
    }

    @PostMapping
    public ResponseEntity<Instruction> saveInstruction(@RequestBody InstructionVo instructionVo) {
        instructionVo.setId(sequenceGeneratorService.generateSequence(Instruction.SEQUENCE_NAME));
        return ResponseEntity.status(HttpStatus.CREATED).body(instructionService.saveOrUpdateInstruction(instructionVo));
    }

    @PutMapping
    public ResponseEntity<Instruction> updateInstruction(@RequestBody InstructionVo instructionVo) {
        return ResponseEntity.status(HttpStatus.OK).body(instructionService.saveOrUpdateInstruction(instructionVo));
    }
}
