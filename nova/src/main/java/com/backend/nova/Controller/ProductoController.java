package com.backend.nova.Controller;


import com.backend.nova.DTO.ProductoDTO.CrearProductoDTO;
import com.backend.nova.DTO.ProductoDTO.ProductoDTO;
import com.backend.nova.Entity.Producto;
import com.backend.nova.Mapper.Mapper;
import com.backend.nova.Service.ProductoService.ProductoService;
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
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/producto")
@Tag(name ="Productos",description = "Gestión de Productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private Mapper productoMapper;

    private final Logger logger = LoggerFactory.getLogger(ProductoController.class);

    @Operation(summary = "Obtiene un listado de todos los productos de la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de coches",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductoDTO.class))))
    })
    @GetMapping("/productos")
    public ResponseEntity<List<ProductoDTO>> getAllProductos(){
        List<Producto> productos = productoService.findAll();
        List<ProductoDTO> productoDTOS = productoMapper.mapList(productos, ProductoDTO.class);
        logger.info("Productos encontrados");
        return ResponseEntity.ok(productoDTOS);
    }

    @Operation(summary = "Obtiene un producto a partir de su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto recuperado correctamente",
                    content = @Content(schema = @Schema(implementation = ProductoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/productos/{id}")
    public ResponseEntity<ProductoDTO> getProductoById(@PathVariable Long id) {
        Producto producto = productoService.findById(id);
        ProductoDTO productoDTO = productoMapper.mapType(producto, ProductoDTO.class);
        logger.info("Producto encontrado con id: "+ id);
        return ResponseEntity.ok(productoDTO);
    }

    @Operation(summary = "Crea un nuevo producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto creado correctamente",
                    content = @Content(schema = @Schema(implementation = ProductoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Error al crear el producto")
    })
    @PostMapping("/productos")
    public ResponseEntity<ProductoDTO> crearProducto(@RequestBody CrearProductoDTO newProducto) {
        Producto producto = productoService.crearProducto(productoMapper.mapType(newProducto, Producto.class));
        ProductoDTO productoDTO = productoMapper.mapType(producto, ProductoDTO.class);
        logger.info("Producto creado con id: " + productoDTO.getId());
        return ResponseEntity.ok(productoDTO);
    }

    @Operation(summary = "Modifica un producto existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto modificado correctamente",
                    content = @Content(schema = @Schema(implementation = ProductoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Error al modificar el producto")
    })
    @PutMapping("/productos/{id}")
    public ResponseEntity<?> modificarProducto(@RequestBody Producto newProducto,
                                               @PathVariable Long id) {
        Map<String, Object> errores = new HashMap<String,Object>();
        Producto producto = null;
        try {
            producto = productoService.modificarProducto(id,newProducto);
            errores.put("mensaje", "Producto modificado");
            logger.info("Producto modificado con id: " + producto.getId());
        } catch (Exception e) {
            errores.put("mensaje", e.getMessage());
            logger.error(errores.toString());
            return ResponseEntity.badRequest().body(errores);
        }
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    @Operation(summary = "Elimina un producto a partir de su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto eliminado correctamente",
                    content = @Content(schema = @Schema(implementation = ProductoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Error al no eliminado el producto")
    })
    @DeleteMapping("/productos/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id) {
        Map<String, Object> errores = new HashMap<String,Object>();
        try {
            productoService.deleteById(id);
            errores.put("mensaje", "Producto eliminado");
            logger.info("Producto eliminado con id: " + id);
        } catch (Exception e) {
            errores.put("mensaje", e.getMessage());
            logger.error(errores.toString());
            return ResponseEntity.badRequest().body(errores);
        }
        return ResponseEntity.ok(errores);
    }

    @Operation(summary = "Obtiene un listado de productos no entregados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de productos no entregados",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductoDTO.class))))
    })
    @GetMapping("/productos/no-entregados")
    public ResponseEntity<List<ProductoDTO>> getProductosNoEntregados() {
        List<Producto> productosNoEntregados = productoService.findProductosNoEntregados();
        List<ProductoDTO> productoDTOS = productoMapper.mapList(productosNoEntregados, ProductoDTO.class);
        logger.info("Productos no entregados encontrados");
        return ResponseEntity.ok(productoDTOS);
    }

}
