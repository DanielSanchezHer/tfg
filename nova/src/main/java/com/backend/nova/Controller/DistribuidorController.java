package com.backend.nova.Controller;


import com.backend.nova.DTO.DistribuidorDTO.DistribuidorDTO;
import com.backend.nova.Entity.Distribuidor;
import com.backend.nova.Mapper.Mapper;
import com.backend.nova.Service.DistribuidorService.DistribuidorService;
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
public class DistribuidorController {

    @Autowired
    private DistribuidorService distribuidorService;

    @Autowired
    private Mapper mapper;

    @GetMapping("/distribuidores")
    public ResponseEntity<List<DistribuidorDTO>> getAllDistribuidores()
    {
        List<Distribuidor> distribuidores = distribuidorService.findAll();
        List<DistribuidorDTO> distribuidoresDTO = mapper.mapList(distribuidores, DistribuidorDTO.class);
        return ResponseEntity.ok(distribuidoresDTO);
    }

    @GetMapping("/distribuidores/{id}")
    public ResponseEntity<DistribuidorDTO> getDistribuidorById(@PathVariable Long id)
    {
        Distribuidor distribuidor = distribuidorService.findById(id);
        DistribuidorDTO distribuidorDTO = mapper.mapType(distribuidor, DistribuidorDTO.class);
        return ResponseEntity.ok(distribuidorDTO);
    }

    @PostMapping("/distribuidores")
    public ResponseEntity<DistribuidorDTO> crearDistribuidor(@RequestBody DistribuidorDTO newDistribuidor)
    {
        Distribuidor distribuidor = distribuidorService.crearDistribuidor(mapper.mapType(newDistribuidor, Distribuidor.class));
        DistribuidorDTO distribuidorDTO = mapper.mapType(distribuidor, DistribuidorDTO.class);
        return ResponseEntity.ok(distribuidorDTO);
    }

    @PutMapping("/distribuidores/{id}")
    public ResponseEntity<?> modificarDistribuidor(@RequestBody Distribuidor newDistribuidor,
                                                   @PathVariable Long id)
    {
        Map<String, Object> errores = new HashMap<String, Object>();
        Distribuidor distribuidor = null;
        try{
            distribuidor = distribuidorService.updateDistribuidor(id,newDistribuidor);
            errores.put("distribuidor", distribuidor);

        }catch (Exception e){
            errores.put("mensaje", e.getMessage());
            errores.put("distribuidor","No se ha podido modificar el distribuidor");
            return new ResponseEntity<Map<String,Object>>(errores , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(distribuidor, HttpStatus.OK);
    }

    @DeleteMapping("/distribuidores/{id}")
    public ResponseEntity<?> eliminarDistribuidor(@PathVariable Long id)
    {
        Map<String, Object> errores = new HashMap<String, Object>();
        try{
            distribuidorService.delete(id);
            errores.put("mensaje", "Distribuidor eliminado");
        }catch (Exception e){
            errores.put("mensaje", e.getMessage());
            errores.put("distribuidor","No se ha podido eliminar el distribuidor");
            return new ResponseEntity<Map<String,Object>>(errores , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
