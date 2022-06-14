package com.uade.recipes.controllers;

import com.uade.recipes.model.Conversion;
import com.uade.recipes.service.conversion.ConversionService;
import com.uade.recipes.vo.ConversionVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/conversion")
public class ConversionController {
    private final ConversionService conversionService;

    public ConversionController(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @GetMapping
    @ApiOperation(value = "Obtener una lista de todas las conversiones donde se le puede pasar opcionalmente un sourceUnit Id y/o un targetUnit Id", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Conversiones retornadas exitosamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "TargetUnit/SourceUnit/Conversion no encontrada")
    }
    )
    public ResponseEntity<List<ConversionVo>> getAllConversions(@RequestParam(required = false) Integer sourceUnitId, @RequestParam(required = false) Integer targetUnitId) {
        if (sourceUnitId != null && targetUnitId == null) {
            List<ConversionVo> result = transformListToVoList(conversionService.getConversionsBySourceUnitId(sourceUnitId));
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else if (sourceUnitId == null && targetUnitId != null) {
            List<ConversionVo> result = transformListToVoList(conversionService.getConversionsByTargetUnitId(targetUnitId));
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else if (sourceUnitId != null && targetUnitId != null) {
            List<ConversionVo> result = transformListToVoList((List<Conversion>) conversionService.getConversionBySourceUnitIdAndTargetUnitId(sourceUnitId, targetUnitId));
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        List<ConversionVo> result = transformListToVoList(conversionService.getAllConversions());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Obtener una conversion por su ID en la base de datos", response = ResponseStatus.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Conversion retornada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "Conversion no encontrada"),

    })
    public ResponseEntity<ConversionVo> getConversionById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(conversionService.getConversionById(id).toVO());
    }

    @PostMapping
    @ApiOperation(value = "Guardar una conversion entre dos unidades", response = ResponseStatus.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Conversion guardada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "Source unit/Target unit no fue encontrado")

    })
    public ResponseEntity<ConversionVo> saveConversion(@RequestBody ConversionVo conversionVo) {//TODO CONTROLAR QUE EL FACTOR CONVERSION ES > 0
        return ResponseEntity.status(HttpStatus.CREATED).body(conversionService.saveOrUpdateConversion(conversionVo).toVO());
    }

    @PutMapping
    @ApiOperation(value = "Actualizar una conversion existente ", response = ResponseStatus.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Actualizacion satisfactoria"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "Conversion no encontrada o Source unit/Target unit no fue encontrado")

    })
    public ResponseEntity<ConversionVo> updateConversion(@RequestBody ConversionVo conversionVo) {
        return ResponseEntity.status(HttpStatus.OK).body(conversionService.saveOrUpdateConversion(conversionVo).toVO());
    }

    private List<ConversionVo> transformListToVoList(List<Conversion> conversions){
        return conversions.stream().map(Conversion::toVO).collect(Collectors.toList());
    }
}
