package com.uade.recipes.exceptions.ExceptionHandlers;

import com.uade.recipes.exceptions.instructionExceptions.InstructionExistsException;
import com.uade.recipes.exceptions.instructionExceptions.InstructionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InstructionExceptionHandlerAdvice {
    @ExceptionHandler(InstructionNotFoundException.class)
    public ResponseEntity handleInstructionNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The Instruction was not found");
    }
    @ExceptionHandler(InstructionExistsException.class)
    public ResponseEntity handleInstructionExistsException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The Instruction already exists");
    }

}
