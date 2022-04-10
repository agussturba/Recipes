package com.uade.recipes.controllers;

import com.uade.recipes.model.Unit;
import com.uade.recipes.service.unit.UnitService;
import com.uade.recipes.vo.UnitVo;
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
    public ResponseEntity<List<Unit>> getAllUnits(){
        return ResponseEntity.status(HttpStatus.OK).body(unitService.getAllUnits());
    }
    @GetMapping("/description/{description}")
    public ResponseEntity<Unit> getUnitByDescription(@PathVariable String description){
        return ResponseEntity.status(HttpStatus.OK).body(unitService.getUnitByDescription(description));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Unit> getUnitById(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(unitService.getUnitById(id));
    }
    @PostMapping
    public ResponseEntity<Unit> saveUnit(@RequestBody UnitVo unitVo){
        return ResponseEntity.status(HttpStatus.OK).body(unitService.saveOrUpdateUnit(unitVo));
    }
    @PutMapping
    public ResponseEntity<Unit> updateUnit(@RequestBody UnitVo unitVo){
        return ResponseEntity.status(HttpStatus.OK).body(unitService.saveOrUpdateUnit(unitVo));
    }
}
