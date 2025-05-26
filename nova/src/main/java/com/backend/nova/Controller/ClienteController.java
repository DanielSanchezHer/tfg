package com.backend.nova.Controller;


import com.backend.nova.DTO.ClienteDTO.ClienteDTO;
import com.backend.nova.DTO.ClienteDTO.ClienteFacturaDTO;
import com.backend.nova.DTO.ClienteDTO.FinalizarCarrito;
import com.backend.nova.Entity.Carrito;
import com.backend.nova.Entity.Cliente;
import com.backend.nova.Exception.Response;
import com.backend.nova.Mapper.Mapper;
import com.backend.nova.Service.ClienteService.ClienteService;
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

@CrossOrigin(origins ={"*"})
@RestController
@RequestMapping("/cliente")
@Tag(name ="Clientes",description = "Gestión de clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private Mapper mapper;
    private final Logger logger = LoggerFactory.getLogger(ClienteController.class);
    @Operation(summary = "Obtiene un listado de todos los clientes de la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de coches",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ClienteDTO.class))))
    })
    @GetMapping("/clientes")
    public ResponseEntity<List<ClienteDTO>> getAllClientes()
    {
        List<Cliente> clientes = clienteService.findAll();
        List<ClienteDTO> clienteDTOS = mapper.mapList(clientes, ClienteDTO.class);
        logger.info("Clientes encontrados");
        return ResponseEntity.ok(clienteDTOS);
    }

    @Operation(summary = "Obtiene un cliente a partir de su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente recuperado correctamente",
                    content = @Content(schema = @Schema(implementation = ClienteDTO.class))),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping("/clientes/{id}")
    public ResponseEntity<ClienteDTO> getClienteById(@PathVariable Long id)
    {
        Cliente cliente = clienteService.findById(id);
        ClienteDTO clienteDTO = mapper.mapType(cliente, ClienteDTO.class);
        logger.info("Cliente encontrado con id: "+ id);
        return ResponseEntity.ok(clienteDTO);
    }

    @Operation(summary = "Crea un nuevo cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente creado correctamente",
                    content = @Content(schema = @Schema(implementation = ClienteDTO.class))),
            @ApiResponse(responseCode = "400", description = "Error al crear el cliente")
    })
    @PostMapping("/clientes")
    public ResponseEntity<ClienteDTO> createCliente(@RequestBody ClienteDTO clienteDTO)
    {
        Cliente cliente = mapper.mapType(clienteDTO, Cliente.class);
        ClienteDTO clienteCreatedDTO = mapper.mapType(cliente, ClienteDTO.class);
        logger.info("Creando cliente: ");
        return ResponseEntity.ok(clienteCreatedDTO);
    }

    @Operation(summary = "Actualiza un cliente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente actualizado correctamente",
                    content = @Content(schema = @Schema(implementation = ClienteDTO.class))),
            @ApiResponse(responseCode = "400", description = "Error al actualizar el cliente")
    })
    @PutMapping("/clientes/{id}")
    public ResponseEntity<?> updateCliente(@PathVariable Long id,
                                           @RequestBody Cliente newCliente)
    {
        Map<String, Object> errores = new HashMap<String,Object>();
        Cliente cliente = null;
        try
        {
            cliente = clienteService.modificarCliente(id, newCliente);
            logger.info("Cliente modificado con id: " + id);
        }
        catch (Exception e)
        {
            errores.put("error", e.getMessage());
            errores.put("mensaje", "Error al modificar el cliente");
            logger.error(errores.toString());
            return new ResponseEntity<Map<String,Object>>(errores , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @Operation(summary = "Elimina un cliente por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente eliminado correctamente",
                    content = @Content(schema = @Schema(implementation = ClienteDTO.class))),
            @ApiResponse(responseCode = "404", description = "Error al eliminar el cliente",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<?> eliminarCliente(@PathVariable Long id)
    {
        Map<String, Object> errores = new HashMap<String,Object>();
        try
        {
            clienteService.deleteById(id);
            logger.info("Cliente eliminado con id: " + id);
        }
        catch (Exception e)
        {
            errores.put("error", e.getMessage());
            errores.put("mensaje", "Error al eliminar el cliente");
            logger.error(errores.toString());
            return new ResponseEntity<Map<String,Object>>(errores , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(errores, HttpStatus.OK);
    }

    @Operation(summary = "Finaliza el carrito activo de un cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrito finalizado correctamente",
                    content = @Content(schema = @Schema(implementation = FinalizarCarrito.class))),
            @ApiResponse(responseCode = "400", description = "Error al finalizar el carrito")
    })
    @PostMapping("/clientes/finalizar-carrito/")
    public ResponseEntity<?> finalizarCarritoCliente(@RequestBody FinalizarCarrito request) {
        Map<String, Object> response = new HashMap<>();
        try
        {
            Carrito carritoFinalizado = clienteService.finalizarCarritoActivo(request.getClienteId());
            logger.info("Carrito finalizado para el cliente con id: ");
            return ResponseEntity.ok(carritoFinalizado);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("message", "Error al finalizar el carrito");
            logger.error(response.toString());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Obtiene los carritos de un cliente junto con sus productos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carritos obtenidos correctamente",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Carrito.class)))),
            @ApiResponse(responseCode = "404", description = "Error al obtener los carritos del cliente")
    })
    @GetMapping("/clientesCarrito/{id}")
    public ResponseEntity<?> getCarritosWithProductosByClienteId(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Carrito> carritos = clienteService.findAllCarritoWithProductoByClienteId(id);
            logger.info("Carritos obtenidos para el cliente con id: " + id);
            return ResponseEntity.ok(carritos);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("message", "Error al obtener los carritos del cliente");
            logger.error(response.toString());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Obtiene el total de la factura de un cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Total de la factura obtenido correctamente",
                    content = @Content(schema = @Schema(implementation = ClienteFacturaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Error al obtener el total de la factura")
    })
    @GetMapping("/clienteFactura/{id}")
    public ResponseEntity<ClienteFacturaDTO> obtenerTotalFactura(@PathVariable Long id) {
        double total = clienteService.calcularTotalCarritoActivo(id);

        ClienteFacturaDTO facturaDTO = new ClienteFacturaDTO();
        facturaDTO.setTotalFactura(total);
        logger.info("Total de la factura obtenido para el cliente con id: " + id);
        return ResponseEntity.ok(facturaDTO);
    }

}
