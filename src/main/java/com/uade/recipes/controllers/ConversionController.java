package com.uade.recipes.controllers;

import com.uade.recipes.model.Conversion;
import com.uade.recipes.service.conversion.ConversionService;
import com.uade.recipes.vo.ConversionVo;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/conversion")
public class ConversionController {
    private final ConversionService conversionService;

    public ConversionController(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @GetMapping
    @ApiOperation(value = "Obtener una lista de todas las conversiones", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Conversiones retornadas exitosamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "Conversion no encontrada")//TODO BUSCAR COMO PONER MAS MENSAJES DE ERROR CON EL MISMO CODIGO
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
            @ApiResponse(code = 302, message = "Conversion retornada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "Conversion no encontrada"),

    })
    public ResponseEntity<ConversionVo> getConversionById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.FOUND).body(conversionService.getConversionById(id).toVO());
    }

    @PostMapping
    @ApiOperation(value = "Guardar una conversion entre dos unidades", response = ResponseStatus.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Conversion guardada satisfactoriamente"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "Conversion no encontrada")//TODO BUSCAR COMO PONER MAS MENSAJES DE ERROR CON EL MISMO CODIGO

    })
    public ResponseEntity<ConversionVo> saveConversion(@RequestBody ConversionVo conversionVo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(conversionService.saveOrUpdateConversion(conversionVo).toVO());
    }

    @PutMapping
    @ApiOperation(value = "Actualizar una conversion existente ", response = ResponseStatus.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Actualizacion satisfactoria"),
            @ApiResponse(code = 401, message = "No esta autorizado a ver este recurso"),
            @ApiResponse(code = 403, message = "Está prohibido acceder al recurso al que intentas acceder"),
            @ApiResponse(code = 404, message = "Conversion no encontrada")//TODO BUSCAR COMO PONER MAS MENSAJES DE ERROR CON EL MISMO CODIGO

    })
    public ResponseEntity<ConversionVo> updateConversion(@RequestBody ConversionVo conversionVo) {
        return ResponseEntity.status(HttpStatus.OK).body(conversionService.saveOrUpdateConversion(conversionVo).toVO());
    }

    private List<ConversionVo> transformListToVoList(List<Conversion> list){
        List<ConversionVo> result = new ArrayList<>();
        for(Conversion conv : list){
            result.add(conv.toVO());
        }
        return result;
    }
}
