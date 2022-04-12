package com.uade.recipes.controllers;

import com.uade.recipes.model.Unit;
import com.uade.recipes.service.unit.UnitService;
import com.uade.recipes.vo.UnitVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/unit")
public class UnitController {
    private final UnitService unitService;

    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    @GetMapping
    @ApiOperation(value = "Get a list of units", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved a list of units"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),

    })
    public ResponseEntity<List<Unit>> getAllUnits() {
        return ResponseEntity.status(HttpStatus.OK).body(unitService.getAllUnits());
    }

    @GetMapping("/description/{description}")
    @ApiOperation(value = "Get a unit by his description", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "Successfully retrieved a unit by his description"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The unit was not found")

    })
    public ResponseEntity<Unit> getUnitByDescription(@PathVariable String description) {
        return ResponseEntity.status(HttpStatus.OK).body(unitService.getUnitByDescription(description));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a unit by his db id", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "Successfully retrieved a unit by his db id"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The unit was not found")

    })
    public ResponseEntity<Unit> getUnitById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(unitService.getUnitById(id));
    }

    @PostMapping
    @ApiOperation(value = "Create a unit", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created a unit"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
    })
    public ResponseEntity<Unit> saveUnit(@RequestBody UnitVo unitVo) {
        return ResponseEntity.status(HttpStatus.OK).body(unitService.saveOrUpdateUnit(unitVo));
    }

    @PutMapping
    @ApiOperation(value = "Update a unit", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated a unit"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
    })
    public ResponseEntity<Unit> updateUnit(@RequestBody UnitVo unitVo) {
        return ResponseEntity.status(HttpStatus.OK).body(unitService.saveOrUpdateUnit(unitVo));
    }
}
