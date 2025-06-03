package com.backend.nova.Controller;

import com.backend.nova.DTO.CarritoDTO.AnyadirProductoCarrito;
import com.backend.nova.DTO.CarritoDTO.CarritoDTO;
import com.backend.nova.DTO.CarritoDTO.CarritoTodosDTO;
import com.backend.nova.DTO.ProductoDTO.CrearProductoDTO;
import com.backend.nova.DTO.ProductoDTO.ProductoCarritoDTO;
import com.backend.nova.Entity.Carrito;
import com.backend.nova.Entity.ContieneNM;
import com.backend.nova.Exception.Response;
import com.backend.nova.Mapper.Mapper;
import com.backend.nova.Service.CarritoService.CarritoService;
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
import java.util.stream.Collectors;

@CrossOrigin(origins ={"*"})
@RestController
@RequestMapping("/carrito")
@Tag(name = "Carrito", description = "Gestión de Carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private Mapper mapper;
    private final Logger logger = LoggerFactory.getLogger(CarritoController.class);

    @Operation(summary = "Obtiene un listado de todos los coches de la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de carrito",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CarritoDTO.class))))
    })
    @GetMapping("/carritos")
    public ResponseEntity<List<CarritoDTO>> getAllCarritos()
    {
        List<Carrito> carritos = carritoService.findAll();
        List<CarritoDTO> carritoDTOS = mapper.mapList(carritos, CarritoDTO.class);
        logger.info("Carritos encontrados");
        return ResponseEntity.ok(carritoDTOS);
    }

    @Operation(summary = "Obtiene un carrito a partir de su matricula")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "carrito recuperado corractamente",
                    content = @Content(schema = @Schema(implementation = CarritoDTO.class))),
            @ApiResponse(responseCode = "404", description = "carrito no encontrado",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping("/carritos/{id}")
    public ResponseEntity<CarritoDTO> getCarritoById(@PathVariable long id)
    {
        Carrito carrito = carritoService.findById(id);
        CarritoDTO carritoDTO = mapper.mapType(carrito, CarritoDTO.class);
        logger.info("Carrito encontrado con ID: "+ id);
        return ResponseEntity.ok(carritoDTO);
    }

    @Operation(summary = "Crea un carrito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "carrito creado corractamente",
                    content = @Content(schema = @Schema(implementation = CarritoDTO.class))),
            @ApiResponse(responseCode = "404", description = "carrito no creado",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PostMapping("/carritos")
    public ResponseEntity<CarritoDTO> createCarrito(@RequestBody CarritoDTO carritoDTO)
    {
        Carrito carrito = mapper.mapType(carritoDTO, Carrito.class);
        CarritoDTO carritoCreatedDTO = mapper.mapType(carrito, CarritoDTO.class);
        logger.info("Carrito creado con ID: ", carritoCreatedDTO.getId());
        return ResponseEntity.ok(carritoCreatedDTO);
    }

    @Operation(summary = "Modifica un carrito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "carrito modificado corractamente",
                    content = @Content(schema = @Schema(implementation = CarritoDTO.class))),
            @ApiResponse(responseCode = "404", description = "carrito no modificado",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PutMapping("/carritos/{id}")
    public ResponseEntity<?> updateCarrito(@PathVariable Long id,
                                           @RequestBody Carrito newCarrito)
    {
        Map<String, Object> errores = new HashMap<String,Object>();
        Carrito carrito = null;
        try
        {
            carrito = carritoService.modificarCarrito(id, newCarrito);
            logger.info("Carrito modificado con ID: " + id);
        }catch (Exception e)
        {
            errores.put("error", e.getMessage());
            errores.put("carrito","Error al modificar carrito");
            logger.info(errores.toString());
            return new ResponseEntity<Map<String,Object>>(errores , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(newCarrito,HttpStatus.OK);
    }

    @Operation(summary = "Elimina un carrito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "carrito Eliminado corractamente",
                    content = @Content(schema = @Schema(implementation = CarritoDTO.class))),
            @ApiResponse(responseCode = "404", description = "carrito no Eliminado",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping("/carritos/{id}")
    public ResponseEntity<?> eliminarCarrito(@PathVariable Long id)
    {
        Map<String, Object> errores = new HashMap<String,Object>();
        try
        {
            carritoService.deleteById(id);
            errores.put("carrito","Carrito eliminado");
            logger.info("Carrito eliminado con ID: " + id);
        }catch (Exception e)
        {
            errores.put("error", e.getMessage());
            errores.put("carrito","Error al eliminar carrito");
            logger.info(errores.toString());
            return new ResponseEntity<Map<String,Object>>(errores , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(errores,HttpStatus.OK);
    }

    @Operation(summary = "Producto agregado correctamente al carrito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "producto agregado al carrito correctamente al carrito",
                    content = @Content(schema = @Schema(implementation = CarritoDTO.class))),
            @ApiResponse(responseCode = "404", description = "producto no agregado al carrito correctamente al carrito",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
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
            logger.info("Producto agregado al carrito con ID: " + carritoDTO.getId());
            return ResponseEntity.ok(carritoDTO);

        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("message", "Error al agregar producto al carrito");
            logger.error("Error al agregar producto al carrito: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Obtener carritos finalizados por cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "carritos finalizados por cliente recuperados correctamente",
                    content = @Content(schema = @Schema(implementation = CarritoDTO.class))),
            @ApiResponse(responseCode = "404", description = "no obtenido carritos finalizados por cliente correctamente",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping("/carritos/cliente/{clienteId}/finalizados")
    public ResponseEntity<List<CarritoTodosDTO>> getCarritosFinalizadosByCliente(@PathVariable Long clienteId) {
        List<Carrito> carritos = carritoService.findFinalizedCarritosByCliente(clienteId);
        List<CarritoTodosDTO> carritoDTOS = mapper.mapList(carritos, CarritoTodosDTO.class);
        logger.info("Carritos finalizados encontrados para el cliente con ID: " + clienteId);
        return ResponseEntity.ok(carritoDTOS);
    }

    @Operation(summary = "Obtener carritos no finalizados por cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtener carritos finalizados por cliente recuperados correctamente",
                    content = @Content(schema = @Schema(implementation = CarritoDTO.class))),
            @ApiResponse(responseCode = "404", description = "no Obteniene carritos finalizados por cliente recuperados correctamente",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping("/carritos/carritosNoFinalizados")
    public ResponseEntity<CarritoDTO> getCarritosNoFinalizados(Long clienteId) {
        Carrito carrito = carritoService.getCarritoAbiertoPorCliente(clienteId);
        CarritoDTO carritoCreatedDTO = mapper.mapType(carrito, CarritoDTO.class);
        logger.info("Carrito no finalizado encontrado para el cliente con ID: " + clienteId);
        return ResponseEntity.ok(carritoCreatedDTO);
    }

    @Operation(summary = "Obtiene los productos de un carrito por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene los productos de un carrito por su ID recuperados correctamente",
                    content = @Content(schema = @Schema(implementation = CarritoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Obtiene los productos de un carrito por su ID no recuperados correctamente",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
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
            logger.info("Productos obtenidos del carrito con ID: " + carritoId);
            return ResponseEntity.ok(contieneNMSDTO);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("message", e.getMessage());
            logger.error(error.toString());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }
}