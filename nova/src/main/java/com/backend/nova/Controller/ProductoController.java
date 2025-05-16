package com.backend.nova.Controller;


import com.backend.nova.DTO.ProductoDTO.CrearProductoDTO;
import com.backend.nova.DTO.ProductoDTO.ProductoDTO;
import com.backend.nova.Entity.Producto;
import com.backend.nova.Mapper.Mapper;
import com.backend.nova.Service.ProductoService.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private Mapper productoMapper;

    @GetMapping("/productos")
    public ResponseEntity<List<ProductoDTO>> getAllProductos(){
        List<Producto> productos = productoService.findAll();
        List<ProductoDTO> productoDTOS = productoMapper.mapList(productos, ProductoDTO.class);
        return ResponseEntity.ok(productoDTOS);
    }

    @GetMapping("/productos/{id}")
    public ResponseEntity<ProductoDTO> getProductoById(@PathVariable Long id) {
        Producto producto = productoService.findById(id);
        ProductoDTO productoDTO = productoMapper.mapType(producto, ProductoDTO.class);
        return ResponseEntity.ok(productoDTO);
    }

    @PostMapping("/productos")
    public ResponseEntity<ProductoDTO> crearProducto(@RequestBody CrearProductoDTO newProducto) {
        Producto producto = productoService.crearProducto(productoMapper.mapType(newProducto, Producto.class));
        ProductoDTO productoDTO = productoMapper.mapType(producto, ProductoDTO.class);
        return ResponseEntity.ok(productoDTO);
    }

    @PutMapping("/productos/{id}")
    public ResponseEntity<?> modificarProducto(@RequestBody Producto newProducto,
                                               @PathVariable Long id) {
        Map<String, Object> errores = new HashMap<String,Object>();
        Producto producto = null;
        try {
            producto = productoService.modificarProducto(id,newProducto);
            errores.put("mensaje", "Producto modificado");
        } catch (Exception e) {
            errores.put("mensaje", e.getMessage());
            return ResponseEntity.badRequest().body(errores);
        }
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    @DeleteMapping("/productos/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id) {
        Map<String, Object> errores = new HashMap<String,Object>();
        try {
            productoService.deleteById(id);
            errores.put("mensaje", "Producto eliminado");
        } catch (Exception e) {
            errores.put("mensaje", e.getMessage());
            return ResponseEntity.badRequest().body(errores);
        }
        return ResponseEntity.ok(errores);
    }

    @GetMapping("/productos/no-entregados")
    public ResponseEntity<List<ProductoDTO>> getProductosNoEntregados() {
        List<Producto> productosNoEntregados = productoService.findProductosNoEntregados();
        List<ProductoDTO> productoDTOS = productoMapper.mapList(productosNoEntregados, ProductoDTO.class);
        return ResponseEntity.ok(productoDTOS);
    }

}
