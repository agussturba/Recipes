package com.uade.recipes.controllers;

import com.uade.recipes.model.Unit;
import com.uade.recipes.service.unit.UnitService;
import com.uade.recipes.vo.UnitVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/unit")
public class UnitController {
    private final UnitService unitService;

    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    @GetMapping
    @ApiOperation(value = "Obtener una lista de todas las unidades", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de unidades retornada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),

    })
    public ResponseEntity<List<UnitVo>> getAllUnits() {
        List<UnitVo> result = transformListToVoList(unitService.getAllUnits());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/description/{description}")
    @ApiOperation(value = "Obtener una unidad por su descripcion", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Unidad retornada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "Unidad no encontrada")

    })
    public ResponseEntity<UnitVo> getUnitByDescription(@PathVariable String description) {
        return ResponseEntity.status(HttpStatus.OK).body(unitService.getUnitByDescription(description).toVO());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Obtener una unidad por su ID", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Unidad retornada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "La Unidad no fue encontrada")

    })
    public ResponseEntity<UnitVo> getUnitById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(unitService.getUnitById(id).toVO());
    }

    @PostMapping
    @ApiOperation(value = "Crear una unidad", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Unidad creada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
    })
    public ResponseEntity<UnitVo> saveUnit(@RequestBody UnitVo unitVo) {
        return ResponseEntity.status(HttpStatus.OK).body(unitService.saveOrUpdateUnit(unitVo).toVO());
    }

    @PutMapping
    @ApiOperation(value = "Actualizar una unidad ", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Unidad actualizada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
    })
    public ResponseEntity<UnitVo> updateUnit(@RequestBody UnitVo unitVo) {
        return ResponseEntity.status(HttpStatus.OK).body(unitService.saveOrUpdateUnit(unitVo).toVO());
    }

    private List<UnitVo> transformListToVoList(List<Unit> list){
        List<UnitVo> result = new ArrayList<>();
        for(Unit obj: list){
            result.add(obj.toVO());
        }
        return result;
    }
}
