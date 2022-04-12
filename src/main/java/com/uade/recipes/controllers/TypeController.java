package com.uade.recipes.controllers;

import com.uade.recipes.model.Type;
import com.uade.recipes.service.type.TypeService;
import com.uade.recipes.vo.TypeVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/type")
public class TypeController {
    private final TypeService typeService;

    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    @ApiOperation(value = "Get a list of types", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved a list of types"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
    })
    public ResponseEntity<List<Type>> getAllTypes() {
        return ResponseEntity.status(HttpStatus.OK).body(typeService.getAllTypes());
    }

    @GetMapping("/description/{description}")
    @ApiOperation(value = "Get a type by his description", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "Successfully retrieved the type"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The type was not found"),

    })
    public ResponseEntity<Type> getTypeByDescription(@PathVariable String description) {
        return ResponseEntity.status(HttpStatus.OK).body(typeService.getTypeByDescription(description));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a type by his db id", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "Successfully retrieved the type"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The type was not found"),

    })
    public ResponseEntity<Type> getTypeById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(typeService.getTypeById(id));
    }

    @PostMapping
    @ApiOperation(value = "Created a new type", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created the type"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),

    })
    public ResponseEntity<Type> saveUnit(@RequestBody TypeVo typeVo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(typeService.saveOrUpdateType(typeVo));
    }

    @PutMapping
    @ApiOperation(value = "Updated a  type", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully updated the type"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),

    })
    public ResponseEntity<Type> updateUnit(@RequestBody TypeVo typeVo) {
        return ResponseEntity.status(HttpStatus.OK).body(typeService.saveOrUpdateType(typeVo));
    }
}
