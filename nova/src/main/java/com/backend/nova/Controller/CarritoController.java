package com.backend.nova.Controller;

import com.backend.nova.DTO.CarritoDTO.AnyadirProductoCarrito;
import com.backend.nova.DTO.CarritoDTO.CarritoDTO;
import com.backend.nova.DTO.ProductoDTO.CrearProductoDTO;
import com.backend.nova.DTO.ProductoDTO.ProductoCarritoDTO;
import com.backend.nova.Entity.Carrito;
import com.backend.nova.Entity.ContieneNM;
import com.backend.nova.Mapper.Mapper;
import com.backend.nova.Service.CarritoService.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins ={"*"})
@RestController
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private Mapper mapper;

    @GetMapping("/carritos")
    public ResponseEntity<List<CarritoDTO>> getAllCarritos()
    {
        List<Carrito> carritos = carritoService.findAll();
        List<CarritoDTO> carritoDTOS = mapper.mapList(carritos, CarritoDTO.class);
        return ResponseEntity.ok(carritoDTOS);
    }

    @GetMapping("/carritos/{id}")
    public ResponseEntity<CarritoDTO> getCarritoById(@PathVariable long id)
    {
        Carrito carrito = carritoService.findById(id);
        CarritoDTO carritoDTO = mapper.mapType(carrito, CarritoDTO.class);
        return ResponseEntity.ok(carritoDTO);
    }

    @PostMapping("/carritos")
    public ResponseEntity<CarritoDTO> createCarrito(@RequestBody CarritoDTO carritoDTO)
    {
        Carrito carrito = mapper.mapType(carritoDTO, Carrito.class);
        CarritoDTO carritoCreatedDTO = mapper.mapType(carrito, CarritoDTO.class);
        return ResponseEntity.ok(carritoCreatedDTO);
    }


    @PutMapping("/carritos/{id}")
    public ResponseEntity<?> updateCarrito(@PathVariable Long id,
                                           @RequestBody Carrito newCarrito)
    {
        Map<String, Object> errores = new HashMap<String,Object>();
        Carrito carrito = null;
        try
        {
            carrito = carritoService.modificarCarrito(id, newCarrito);
        }catch (Exception e)
        {
            errores.put("error", e.getMessage());
            errores.put("carrito","Error al modificar carrito");
            return new ResponseEntity<Map<String,Object>>(errores , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(newCarrito,HttpStatus.OK);
    }

    @DeleteMapping("/carritos/{id}")
    public ResponseEntity<?> eliminarCarrito(@PathVariable Long id)
    {
        Map<String, Object> errores = new HashMap<String,Object>();
        try
        {
            carritoService.deleteById(id);
            errores.put("carrito","Carrito eliminado");
        }catch (Exception e)
        {
            errores.put("error", e.getMessage());
            errores.put("carrito","Error al eliminar carrito");
            return new ResponseEntity<Map<String,Object>>(errores , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(errores,HttpStatus.OK);
    }
    @PostMapping("/carritos/agregar-producto")
    public ResponseEntity<?> agregarProductoAlCarrito(
            @RequestBody AnyadirProductoCarrito request) {

        Map<String, Object> response = new HashMap<>();
        try {
            Carrito carrito = carritoService.agregarProductoAlCarrito(
                    request.getClienteId(),
                    request.getProductoId(),
                    request.getCantidad()
            );

            CarritoDTO carritoDTO = mapper.mapType(carrito, CarritoDTO.class);
            return ResponseEntity.ok(carritoDTO);

        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("message", "Error al agregar producto al carrito");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/carritos/cliente/{clienteId}/finalizados")
    public ResponseEntity<List<CarritoDTO>> getCarritosFinalizadosByCliente(@PathVariable Long clienteId) {
        List<Carrito> carritos = carritoService.findFinalizedCarritosByCliente(clienteId);
        List<CarritoDTO> carritoDTOS = mapper.mapList(carritos, CarritoDTO.class);
        return ResponseEntity.ok(carritoDTOS);
    }

    @GetMapping("/carritos/carritosNoFinalizados")
    public ResponseEntity<CarritoDTO> getCarritosNoFinalizados(Long clienteId) {
        Carrito carrito = carritoService.getCarritoAbiertoPorCliente(clienteId);
        CarritoDTO carritoCreatedDTO = mapper.mapType(carrito, CarritoDTO.class);
        return ResponseEntity.ok(carritoCreatedDTO);
    }

    @GetMapping("/carritos/{carritoId}/productos")
    public ResponseEntity<?> getProductosDeCarrito(@PathVariable Long carritoId) {
        try {
            List<ContieneNM> contieneNMS = carritoService.getProductosPorCarrito(carritoId);
            List<ProductoCarritoDTO> contieneNMSDTO = contieneNMS.stream().map(contiene -> {
                ProductoCarritoDTO dto = new ProductoCarritoDTO();
                dto.setId(contiene.getProducto().getId());
                dto.setNombre(contiene.getProducto().getNombre());
                dto.setPrecio(contiene.getCantidad());
                dto.setDescripcion(contiene.getProducto().getDescripcion());
                dto.setPrecio(contiene.getProducto().getPrecio());
                return dto;
            }).collect(Collectors.toList());

            return ResponseEntity.ok(contieneNMSDTO);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }
}
/*
@GetMapping("/carritos/{carritoId}/productos")
    public ResponseEntity<?> getProductosDeCarrito(@PathVariable Long carritoId) {
        try {
            List<ContieneNM> contieneNMS = carritoService.getProductosPorCarrito(carritoId);
            List<ProductoCarritoDTO> contieneNMSDTO = mapper.mapList(contieneNMS, ProductoCarritoDTO.class);
            return ResponseEntity.ok(contieneNMSDTO);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }
 */