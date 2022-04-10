package com.uade.recipes.controllers;

import com.uade.recipes.model.Type;
import com.uade.recipes.service.type.TypeService;
import com.uade.recipes.vo.TypeVo;
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
    public ResponseEntity<List<Type>> getAllTypes(){
        return ResponseEntity.status(HttpStatus.OK).body(typeService.getAllTypes());
    }
    @GetMapping("/description/{description}")
    public ResponseEntity<Type> getTypeByDescription(@PathVariable String description){
        return ResponseEntity.status(HttpStatus.OK).body(typeService.getTypeByDescription(description));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Type> getTypeById(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(typeService.getTypeById(id));
    }
    @PostMapping
    public ResponseEntity<Type> saveUnit(@RequestBody TypeVo typeVo){
        return ResponseEntity.status(HttpStatus.CREATED).body(typeService.saveOrUpdateType(typeVo));
    }
    @PutMapping
    public ResponseEntity<Type> updateUnit(@RequestBody TypeVo typeVo){
        return ResponseEntity.status(HttpStatus.OK).body(typeService.saveOrUpdateType(typeVo));
    }
}
