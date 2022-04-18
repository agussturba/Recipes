package com.uade.recipes.controllers;

import com.uade.recipes.exceptions.instructionExceptions.InstructionNotFoundException;
import com.uade.recipes.model.Instruction;
import com.uade.recipes.model.Multimedia;
import com.uade.recipes.service.multimedia.MultimediaService;
import com.uade.recipes.vo.InstructionVo;
import com.uade.recipes.vo.MultimediaVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/multimedia")
public class MultimediaController {
    private final MultimediaService multimediaService;

    public MultimediaController(MultimediaService multimediaService) {
        this.multimediaService = multimediaService;
    }


    @GetMapping
    @ApiOperation(value = "Retornar una lista con toda la multimedia", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de multimedia retornada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
    })
    public ResponseEntity<List<MultimediaVo>> getAllMultimedia(){
        List<MultimediaVo> result = transformListToVoList(multimediaService.getAllMultimedia());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    @GetMapping("/{id}")
    @ApiOperation(value = "Retornar una multimedia por su ID", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Multimedia retornada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "La multimedia no fue encontrada")
    })
    public ResponseEntity<MultimediaVo> getMultimediaById(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.FOUND).body(multimediaService.getMultimediaById(id).toVO());
    }
    @GetMapping("/instruction/{id}")
    @ApiOperation(value = "Retornar una multimedia por su receta", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Multimedia retornada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "La multimedia no fue encontrada")
    })
    public ResponseEntity<MultimediaVo> getMultimediaByInstructionId(@PathVariable Integer id) throws InstructionNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(multimediaService.getMultimediaByInstructionId(id).toVO());
    }
    @PostMapping
    @ApiOperation(value = "Crear una multimedia para una instruccion", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Multimedia creada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "La instruccion no fue encontrada")
    })
    public ResponseEntity<List<Multimedia>> saveMultimedia(@RequestParam Integer instructionId, @RequestParam List<MultipartFile> multimedia) throws InstructionNotFoundException, IOException {
        return ResponseEntity.status(HttpStatus.FOUND).body((List<Multimedia>) multimediaService.saveMultimedia(instructionId, multimedia));
    }

    @DeleteMapping
    public ResponseEntity deleteMultimedia(@RequestParam Integer multimediaId) throws IOException {
        multimediaService.deleteMultimedia(multimediaId);
        return new ResponseEntity(HttpStatus.OK);
    }

    private List<MultimediaVo> transformListToVoList(List<Multimedia> list){
        List<MultimediaVo> result = new ArrayList<>();
        for(Multimedia mult: list){
            result.add(mult.toVO());
        }
        return result;
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
