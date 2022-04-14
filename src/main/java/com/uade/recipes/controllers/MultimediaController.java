package com.uade.recipes.controllers;

import com.uade.recipes.exceptions.instructionExceptions.InstructionNotFoundException;
import com.uade.recipes.model.Multimedia;
import com.uade.recipes.service.multimedia.MultimediaService;
import com.uade.recipes.vo.MultimediaVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/multimedia")
public class MultimediaController {
    private final MultimediaService multimediaService;

    public MultimediaController(MultimediaService multimediaService) {
        this.multimediaService = multimediaService;
    }


    @GetMapping
    @ApiOperation(value = "Retrieve a list of multimedia", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved a list of multimedia"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
    })
    public ResponseEntity<List<Multimedia>> getAllMultimedia(){
        return ResponseEntity.status(HttpStatus.OK).body(multimediaService.getAllMultimedia());
    }
    @GetMapping("/{id}")
    @ApiOperation(value = "Retrieve a multimedia (photo or video of a recipe) by its db id", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "Successfully retrieved the multimedia"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The Multimedia was not found")
    })
    public ResponseEntity<Multimedia> getMultimediaById(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.FOUND).body(multimediaService.getMultimediaById(id));
    }
    @GetMapping("/instruction/{id}")
    @ApiOperation(value = "Retrieve a multimedia (photo or video of a recipe) by his recipe", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "Successfully retrieved the multimedia"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The instruction was not found")
    })
    public ResponseEntity<Multimedia> getMultimediaByInstructionId(@PathVariable Integer id) throws InstructionNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(multimediaService.getMultimediaByInstructionId(id));
    }
    @PostMapping
    @ApiOperation(value = "Create a new multimedia for a instruction", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created a new multimedia"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The instruction was not found")
    })
    public ResponseEntity<List<Multimedia>> saveMultimedia(@RequestParam Integer instructionId, @RequestParam List<MultipartFile> multimedia) throws InstructionNotFoundException, IOException {
        return ResponseEntity.status(HttpStatus.FOUND).body((List<Multimedia>) multimediaService.saveMultimedia(instructionId, multimedia));
    }

    @DeleteMapping
    public ResponseEntity deleteMultimedia(@RequestParam Integer multimediaId) throws IOException {
        multimediaService.deleteMultimedia(multimediaId);
        return new ResponseEntity(HttpStatus.OK);
    }

//    @PutMapping
//    @ApiOperation(value = "Update a multimedia of an instruction", response = ResponseEntity.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successfully updated a multimedia"),
//            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
//            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
//            @ApiResponse(code = 404, message = "The instruction/multimedia was not found")
//    })
//    public ResponseEntity<Multimedia> updateMultimedia(@RequestBody MultimediaVo multimediaVo) throws InstructionNotFoundException {
//        return ResponseEntity.status(HttpStatus.FOUND).body(multimediaService.saveMultimedia(, multimediaVo, ));
//    }
}
