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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
    @ApiOperation(value = "Retornar una multimedia por el id de la instrucción", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Multimedia retornada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "La multimedia o instrucción no fue encontrada")
    })
    public ResponseEntity<MultimediaVo> getMultimediaByInstructionId(@PathVariable Integer id) throws InstructionNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(multimediaService.getMultimediaByInstructionId(id).toVO());
    }
    @PostMapping
    @ApiOperation(value = "Crear una multimedia para una instrucción", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Multimedia creada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "La instrucción no fue encontrada")
    })
    public ResponseEntity<List<Multimedia>> saveMultimedia(@RequestParam Integer instructionId, @RequestParam List<MultipartFile> multimedia) throws InstructionNotFoundException, IOException {
        return ResponseEntity.status(HttpStatus.FOUND).body((List<Multimedia>) multimediaService.saveMultimedia(instructionId, multimedia));
    }

    @DeleteMapping
    @ApiOperation(value = "Eliminar la multimedia de una instrucción por el id de la multimedia", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Multimedia eliminada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "La instrucción no fue encontrada")
    })
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

}
