package com.uade.recipes.controllers;

import com.uade.recipes.model.Conversion;
import com.uade.recipes.service.conversion.ConversionService;
import com.uade.recipes.vo.ConversionVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conversion")
public class ConversionController {
    private final ConversionService conversionService;

    public ConversionController(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @GetMapping
    public ResponseEntity<List<Conversion>> getAllConversions(@RequestParam(required = false) Integer sourceUnitId, @RequestParam(required = false) Integer targetUnitId) {
        if (sourceUnitId != null && targetUnitId == null) {
            return ResponseEntity.status(HttpStatus.OK).body(conversionService.getConversionsBySourceUnitId(sourceUnitId));
        } else if (sourceUnitId == null && targetUnitId != null) {
            return ResponseEntity.status(HttpStatus.OK).body(conversionService.getConversionsByTargetUnitId(targetUnitId));
        } else if (sourceUnitId != null && targetUnitId != null) {
            return ResponseEntity.status(HttpStatus.OK).body(conversionService.getConversionsBySourceUnitIdAndTargetUnitId(sourceUnitId, targetUnitId));
        }
        return ResponseEntity.status(HttpStatus.OK).body(conversionService.getAllConversions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conversion> getConversionById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.FOUND).body(conversionService.getConversionById(id));
    }

    @PostMapping
    public ResponseEntity<Conversion> saveConversion(@RequestBody ConversionVo conversionVo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(conversionService.saveOrUpdateConversion(conversionVo));
    }

    @PutMapping
    public ResponseEntity<Conversion> updateConversion(@RequestBody ConversionVo conversionVo) {
        return ResponseEntity.status(HttpStatus.OK).body(conversionService.saveOrUpdateConversion(conversionVo));
    }
}
