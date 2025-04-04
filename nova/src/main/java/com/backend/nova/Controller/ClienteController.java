package com.backend.nova.Controller;


import com.backend.nova.DTO.ClienteDTO.ClienteDTO;
import com.backend.nova.DTO.ClienteDTO.FinalizarCarrito;
import com.backend.nova.Entity.Carrito;
import com.backend.nova.Entity.Cliente;
import com.backend.nova.Mapper.Mapper;
import com.backend.nova.Service.ClienteService.ClienteService;
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
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private Mapper mapper;

    @GetMapping("/clientes")
    public ResponseEntity<List<ClienteDTO>> getAllClientes()
    {
        List<Cliente> clientes = clienteService.findAll();
        List<ClienteDTO> clienteDTOS = mapper.mapList(clientes, ClienteDTO.class);
        return ResponseEntity.ok(clienteDTOS);
    }

    @GetMapping("/clientes/{id}")
    public ResponseEntity<ClienteDTO> getClienteById(@PathVariable Long id)
    {
        Cliente cliente = clienteService.findById(id);
        ClienteDTO clienteDTO = mapper.mapType(cliente, ClienteDTO.class);
        return ResponseEntity.ok(clienteDTO);
    }

    @PostMapping("/clientes")
    public ResponseEntity<ClienteDTO> createCliente(@RequestBody ClienteDTO clienteDTO)
    {
        Cliente cliente = mapper.mapType(clienteDTO, Cliente.class);
        ClienteDTO clienteCreatedDTO = mapper.mapType(cliente, ClienteDTO.class);
        return ResponseEntity.ok(clienteCreatedDTO);
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<?> updateCliente(@PathVariable Long id,
                                           @RequestBody Cliente newCliente)
    {
        Map<String, Object> errores = new HashMap<String,Object>();
        Cliente cliente = null;
        try
        {
            cliente = clienteService.modificarCliente(id, newCliente);
        }
        catch (Exception e)
        {
            errores.put("error", e.getMessage());
            errores.put("mensaje", "Error al modificar el cliente");
            return new ResponseEntity<Map<String,Object>>(errores , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<?> eliminarCliente(@PathVariable Long id)
    {
        Map<String, Object> errores = new HashMap<String,Object>();
        try
        {
            clienteService.deleteById(id);
        }
        catch (Exception e)
        {
            errores.put("error", e.getMessage());
            errores.put("mensaje", "Error al eliminar el cliente");
            return new ResponseEntity<Map<String,Object>>(errores , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(errores, HttpStatus.OK);
    }

    @PostMapping("/clientes/finalizar-carrito/")
    public ResponseEntity<?> finalizarCarritoCliente(@RequestBody FinalizarCarrito request) {
        Map<String, Object> response = new HashMap<>();
        try
        {
            Carrito carritoFinalizado = clienteService.finalizarCarritoActivo(request.getClienteId());
            return ResponseEntity.ok(carritoFinalizado);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("message", "Error al finalizar el carrito");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
