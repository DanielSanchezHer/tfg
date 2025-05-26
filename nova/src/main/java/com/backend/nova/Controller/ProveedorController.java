package com.backend.nova.Controller;


import com.backend.nova.DTO.ProveedorDTO.ProveedorDTO;
import com.backend.nova.Entity.Proveedor;
import com.backend.nova.Mapper.Mapper;
import com.backend.nova.Service.ProveedorService.ProveedorService;
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
@RequestMapping("/proveedor")
@Tag(name = "Proveedores", description = "Gestión de Proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private Mapper mapper;

    private final Logger logger = LoggerFactory.getLogger(ProveedorController.class);

    @Operation(summary = "Obtiene un listado de todos los proveedores de la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de coches",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProveedorDTO.class))))
    })
    @GetMapping("/proveedores")
    public ResponseEntity<List<ProveedorDTO>> getAllProveedores(){
        List<Proveedor> proveedores = proveedorService.findAll();
        List<ProveedorDTO> proveedorDTOS = mapper.mapList(proveedores, ProveedorDTO.class);
        logger.info("Proveedores encontrados");
        return ResponseEntity.ok(proveedorDTOS);
    }

    @Operation(summary = "Obtiene un proveedor a partir de su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedor recuperado correctamente",
                    content = @Content(schema = @Schema(implementation = ProveedorDTO.class))),
            @ApiResponse(responseCode = "404", description = "Proveedor no encontrado")
    })
    @GetMapping("/proveedores/{id}")
    public ResponseEntity<ProveedorDTO> getProveedorById(@PathVariable Long id) {
        Proveedor proveedor = proveedorService.findById(id);
        ProveedorDTO proveedorDTO = mapper.mapType(proveedor, ProveedorDTO.class);
        logger.info("Proveedor encontrado con id: "+ id);
        return ResponseEntity.ok(proveedorDTO);
    }

    @Operation(summary = "Crea un nuevo proveedor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedor creado correctamente",
                    content = @Content(schema = @Schema(implementation = ProveedorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Error al crear el proveedor")
    })
    @PostMapping("/proveedores")
    public ResponseEntity<ProveedorDTO> crearProveedor(@RequestBody ProveedorDTO newProveedor) {
        Proveedor proveedor = proveedorService.crearProveedor(mapper.mapType(newProveedor, Proveedor.class));
        ProveedorDTO proveedorDTO = mapper.mapType(proveedor, ProveedorDTO.class);
        logger.info("Proveedor creado con id: " + proveedorDTO.getId());
        return ResponseEntity.ok(proveedorDTO);
    }

    @Operation(summary = "Modifica un proveedor existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedor modificado correctamente",
                    content = @Content(schema = @Schema(implementation = ProveedorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Error al modificar el proveedor")
    })
    @PutMapping("/proveedores/{id}")
    public ResponseEntity<?> modificarProveedor(@RequestBody Proveedor newProveedor,
                                                @PathVariable Long id) {
        Map<String, Object> errores = new HashMap<String,Object>();
        Proveedor proveedor = null;
        try {
            proveedor = proveedorService.modificarProveedor(id,newProveedor);
            errores.put("mensaje", "Proveedor modificado");
            logger.info("Proveedor modificado con id: " + proveedor.getId());
        } catch (Exception e) {
            errores.put("error ", e.getMessage());
            errores.put("proveedor ", "Error al modificar el proveedor");
            logger.error("Error al modificar el proveedor con id: " + id, e);
            return new ResponseEntity<Map<String,Object>>(errores , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(proveedor, HttpStatus.OK);
    }

    @Operation(summary = "Elimina un proveedor a partir de su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedor eliminado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error al eliminar el proveedor")
    })
    @DeleteMapping("/proveedores/{id}")
    public ResponseEntity<?> eliminarProveedor(@PathVariable Long id)
    {
        Map<String, Object> errores = new HashMap<String,Object>();
        try
        {
            proveedorService.deleteById(id);
            errores.put("proveedor ", "Proveedor eliminado");
            logger.info("Proveedor eliminado con id: " + id);
        } catch (Exception e)
        {
            errores.put("error ", e.getMessage());
            errores.put("proveedor ", "Error al eliminar el proveedor");
            logger.error("Error al eliminar el proveedor con id: " + id, e);
            return new ResponseEntity<Map<String,Object>>(errores , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(errores, HttpStatus.OK);
    }
}
