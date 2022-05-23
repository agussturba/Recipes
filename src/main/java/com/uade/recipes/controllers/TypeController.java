package com.uade.recipes.controllers;

import com.uade.recipes.model.RecipePhoto;
import com.uade.recipes.model.Type;
import com.uade.recipes.service.type.TypeService;
import com.uade.recipes.vo.TypeVo;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/type")
public class TypeController {
    private final TypeService typeService;

    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    @GetMapping
    @ApiOperation(value = "Obtener una lista de todos los tipos", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de tipos retornada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
    })
    public ResponseEntity<List<TypeVo>> getAllTypes() {
        List<TypeVo> result = transformListToVoList(typeService.getAllTypes());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/description/{description}")
    @ApiOperation(value = "Obtener un tipo por su descripcion", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tipo retornado satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "El tipo no fue encontrado"),

    })
    public ResponseEntity<TypeVo> getTypeByDescription(@PathVariable String description) {
        return ResponseEntity.status(HttpStatus.OK).body(typeService.getTypeByDescription(description).toVO());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Obtener un tipo por su ID", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tipo retornado satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "El tipo no fue encontrado"),

    })
    public ResponseEntity<TypeVo> getTypeById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(typeService.getTypeById(id).toVO());
    }

    @PostMapping
    @ApiOperation(value = "Crear un nuevo tipo", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tipo creado satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),

    })
    public ResponseEntity<TypeVo> saveType(@RequestBody TypeVo typeVo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(typeService.saveOrUpdateType(typeVo).toVO());
    }

    @PutMapping
    @ApiOperation(value = "Actualizar un tipo", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tipo actualizado satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),

    })
    public ResponseEntity<TypeVo> updateUnit(@RequestBody TypeVo typeVo) {
        return ResponseEntity.status(HttpStatus.OK).body(typeService.saveOrUpdateType(typeVo).toVO());
    }

    private List<TypeVo> transformListToVoList(List<Type> list){
        return list.stream().map(Type::toVO).collect(Collectors.toList());
    }
}
