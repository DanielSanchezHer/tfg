package com.backend.nova.Controller;


import com.backend.nova.DTO.DistribuidorDTO.DistribuidorDTO;
import com.backend.nova.Entity.Distribuidor;
import com.backend.nova.Mapper.Mapper;
import com.backend.nova.Service.DistribuidorService.DistribuidorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/distribuidor")
@Tag(name ="Coches",description = "Gestión de Distribuidor")
public class DistribuidorController {

    @Autowired
    private DistribuidorService distribuidorService;

    @Autowired
    private Mapper mapper;

    private final Logger logger = LoggerFactory.getLogger(DistribuidorController.class);
    @Operation(summary = "Obtiene un listado de todos los distribuidores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de coches",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = DistribuidorDTO.class))))
    })
    @GetMapping("/distribuidores")
    public ResponseEntity<List<DistribuidorDTO>> getAllDistribuidores()
    {
        List<Distribuidor> distribuidores = distribuidorService.findAll();
        List<DistribuidorDTO> distribuidoresDTO = mapper.mapList(distribuidores, DistribuidorDTO.class);
        logger.info("Distribuidores encontrados");
        return ResponseEntity.ok(distribuidoresDTO);
    }

    @Operation(summary = "Obtiene un distribuidor a partir de su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Distribuidor recuperado correctamente",
                    content = @Content(schema = @Schema(implementation = DistribuidorDTO.class))),
            @ApiResponse(responseCode = "404", description = "Distribuidor no encontrado")
    })
    @GetMapping("/distribuidores/{id}")
    public ResponseEntity<DistribuidorDTO> getDistribuidorById(@PathVariable Long id)
    {
        Distribuidor distribuidor = distribuidorService.findById(id);
        DistribuidorDTO distribuidorDTO = mapper.mapType(distribuidor, DistribuidorDTO.class);
        logger.info("Distribuidor encontrado con id: "+ id);
        return ResponseEntity.ok(distribuidorDTO);
    }

    @Operation(summary = "Crea un nuevo distribuidor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Distribuidor creado correctamente",
                    content = @Content(schema = @Schema(implementation = DistribuidorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Error al crear el distribuidor")
    })
    @PostMapping("/distribuidores")
    public ResponseEntity<DistribuidorDTO> crearDistribuidor(@RequestBody DistribuidorDTO newDistribuidor)
    {
        Distribuidor distribuidor = distribuidorService.crearDistribuidor(mapper.mapType(newDistribuidor, Distribuidor.class));
        DistribuidorDTO distribuidorDTO = mapper.mapType(distribuidor, DistribuidorDTO.class);
        logger.info("Distribuidor creado con id: " + distribuidorDTO.getId());
        return ResponseEntity.ok(distribuidorDTO);
    }
    @Operation(summary = "Modifica un distribuidor existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Distribuidor modificado correctamente",
                    content = @Content(schema = @Schema(implementation = DistribuidorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Error al modificar el distribuidor")
    })
    @PutMapping("/distribuidores/{id}")
    public ResponseEntity<?> modificarDistribuidor(@RequestBody Distribuidor newDistribuidor,
                                                   @PathVariable Long id)
    {
        Map<String, Object> errores = new HashMap<String, Object>();
        Distribuidor distribuidor = null;
        try{
            distribuidor = distribuidorService.updateDistribuidor(id,newDistribuidor);
            errores.put("distribuidor", distribuidor);
            logger.info(errores.toString());
        }catch (Exception e){
            errores.put("mensaje", e.getMessage());
            errores.put("distribuidor","No se ha podido modificar el distribuidor");
            logger.error(errores.toString());
            return new ResponseEntity<Map<String,Object>>(errores , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(distribuidor, HttpStatus.OK);
    }

    @Operation(summary = "Elimina un distribuidor a partir de su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Distribuidor eliminado correctamente",
            content = @Content(schema = @Schema(implementation = DistribuidorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Error al eliminar el distribuidor")
    })
    @DeleteMapping("/distribuidores/{id}")
    public ResponseEntity<?> eliminarDistribuidor(@PathVariable Long id)
    {
        Map<String, Object> errores = new HashMap<String, Object>();
        try{
            distribuidorService.delete(id);
            errores.put("mensaje", "Distribuidor eliminado");
            logger.info(errores.toString());
        }catch (Exception e){
            errores.put("mensaje", e.getMessage());
            errores.put("distribuidor","No se ha podido eliminar el distribuidor");
            logger.error(errores.toString());
            return new ResponseEntity<Map<String,Object>>(errores , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
