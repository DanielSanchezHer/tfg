package com.backend.nova.Controller;


import com.backend.nova.DTO.ProveedorDTO.ProveedorDTO;
import com.backend.nova.Entity.Proveedor;
import com.backend.nova.Mapper.Mapper;
import com.backend.nova.Service.ProveedorService.ProveedorService;
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
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private Mapper mapper;

    @GetMapping("/proveedores")
    public ResponseEntity<List<ProveedorDTO>> getAllProveedores(){
        List<Proveedor> proveedores = proveedorService.findAll();
        List<ProveedorDTO> proveedorDTOS = mapper.mapList(proveedores, ProveedorDTO.class);
        return ResponseEntity.ok(proveedorDTOS);
    }

    @GetMapping("/proveedores/{id}")
    public ResponseEntity<ProveedorDTO> getProveedorById(@PathVariable Long id) {
        Proveedor proveedor = proveedorService.findById(id);
        ProveedorDTO proveedorDTO = mapper.mapType(proveedor, ProveedorDTO.class);
        return ResponseEntity.ok(proveedorDTO);
    }

    @PostMapping("/proveedores")
    public ResponseEntity<ProveedorDTO> crearProveedor(@RequestBody ProveedorDTO newProveedor) {
        Proveedor proveedor = proveedorService.crearProveedor(mapper.mapType(newProveedor, Proveedor.class));
        ProveedorDTO proveedorDTO = mapper.mapType(proveedor, ProveedorDTO.class);
        return ResponseEntity.ok(proveedorDTO);
    }

    @PutMapping("/proveedores/{id}")
    public ResponseEntity<?> modificarProveedor(@RequestBody Proveedor newProveedor,
                                                @PathVariable Long id) {
        Map<String, Object> errores = new HashMap<String,Object>();
        Proveedor proveedor = null;
        try {
            proveedor = proveedorService.modificarProveedor(id,newProveedor);
            errores.put("mensaje", "Proveedor modificado");
        } catch (Exception e) {
            errores.put("error ", e.getMessage());
            errores.put("proveedor ", "Error al modificar el proveedor");
            return new ResponseEntity<Map<String,Object>>(errores , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(proveedor, HttpStatus.OK);
    }

    @DeleteMapping("/proveedores/{id}")
    public ResponseEntity<?> eliminarProveedor(@PathVariable Long id)
    {
        Map<String, Object> errores = new HashMap<String,Object>();
        try
        {
            proveedorService.deleteById(id);
            errores.put("proveedor ", "Proveedor eliminado");
        } catch (Exception e)
        {
            errores.put("error ", e.getMessage());
            errores.put("proveedor ", "Error al eliminar el proveedor");
            return new ResponseEntity<Map<String,Object>>(errores , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(errores, HttpStatus.OK);
    }
}
