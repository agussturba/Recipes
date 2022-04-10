package com.uade.recipes.controllers;

import com.uade.recipes.exceptions.instructionExceptions.InstructionNotFoundException;
import com.uade.recipes.model.Multimedia;
import com.uade.recipes.service.multimedia.MultimediaService;
import com.uade.recipes.vo.MultimediaVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/multimedia")
public class MultimediaController {
    private final MultimediaService multimediaService;

    public MultimediaController(MultimediaService multimediaService) {
        this.multimediaService = multimediaService;
    }


    @GetMapping
    public ResponseEntity<List<Multimedia>> getAllMultimedia(){
        return ResponseEntity.status(HttpStatus.OK).body(multimediaService.getAllMultimedia());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Multimedia> getMultimediaById(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.FOUND).body(multimediaService.getMultimediaById(id));
    }
    @GetMapping("/instruction/{id}")
    public ResponseEntity<Multimedia> getMultimediaByInstructionId(@PathVariable Integer id) throws InstructionNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(multimediaService.getMultimediaByInstructionId(id));
    }
    @PostMapping
    public ResponseEntity<Multimedia> saveMultimedia(@RequestBody MultimediaVo multimediaVo) throws InstructionNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(multimediaService.saveOrUpdateMultimedia(multimediaVo));
    }
    @PutMapping
    public ResponseEntity<Multimedia> updateMultimedia(@RequestBody MultimediaVo multimediaVo) throws InstructionNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(multimediaService.saveOrUpdateMultimedia(multimediaVo));
    }
}
