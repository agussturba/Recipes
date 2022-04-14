package com.uade.recipes.controllers;

import com.uade.recipes.model.Conversion;
import com.uade.recipes.service.conversion.ConversionService;
import com.uade.recipes.vo.ConversionVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraReactiveRepositoriesAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @ApiOperation(value = "Get a list of all conversions", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all conversions"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "Unit Not Found")//TODO BUSCAR COMO PONER MAS MENSAJES DE ERROR CON EL MISMO CODIGO
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
            List<ConversionVo> result = transformListToVoList(conversionService.getConversionsBySourceUnitIdAndTargetUnitId(sourceUnitId, targetUnitId));
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        List<ConversionVo> result = transformListToVoList(conversionService.getAllConversions());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a conversion base on his db id", response = ResponseStatus.class)
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "Successfully retrieved the conversion"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "Conversion Not Found"),

    })
    public ResponseEntity<ConversionVo> getConversionById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.FOUND).body(conversionService.getConversionById(id).toVO());
    }

    @PostMapping
    @ApiOperation(value = "Save a new conversion between two different units ", response = ResponseStatus.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created a new conversion"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "Unit Not Found")//TODO BUSCAR COMO PONER MAS MENSAJES DE ERROR CON EL MISMO CODIGO

    })
    public ResponseEntity<ConversionVo> saveConversion(@RequestBody ConversionVo conversionVo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(conversionService.saveOrUpdateConversion(conversionVo).toVO());
    }

    @PutMapping
    @ApiOperation(value = "Update an already existing conversion between two different units ", response = ResponseStatus.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully updated a new conversion"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "Unit Not Found")//TODO BUSCAR COMO PONER MAS MENSAJES DE ERROR CON EL MISMO CODIGO

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
