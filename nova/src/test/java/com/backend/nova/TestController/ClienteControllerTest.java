package com.backend.nova.TestController;

import com.backend.nova.Controller.ClienteController;
import com.backend.nova.DTO.ClienteDTO.ClienteDTO;
import com.backend.nova.DTO.ClienteDTO.FinalizarCarrito;
import com.backend.nova.Entity.Carrito;
import com.backend.nova.Entity.Cliente;
import com.backend.nova.Mapper.Mapper;
import com.backend.nova.Service.ClienteService.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ClienteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ClienteService clienteService;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private ClienteController clienteController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController).build();
    }

    @Test
    void testGetAllClientes() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Juan");
        cliente.setApellido("Pérez");

        ClienteDTO clienteDTO = new ClienteDTO(1L, "Juan", "Pérez", Collections.emptyList());

        List<Cliente> clientes = Arrays.asList(cliente);
        List<ClienteDTO> clienteDTOList = Arrays.asList(clienteDTO);

        when(clienteService.findAll()).thenReturn(clientes);
        when(mapper.mapList(clientes, ClienteDTO.class)).thenReturn(clienteDTOList);

        mockMvc.perform(get("/cliente/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Juan"));
    }

          @Test
    void testCreateCliente() throws Exception {
        ClienteDTO inputDTO = new ClienteDTO(0, "Ana", "Gómez", null);
        Cliente clienteEntity = new Cliente();
        clienteEntity.setNombre("Ana");
        clienteEntity.setApellido("Gómez");

        ClienteDTO savedDTO = new ClienteDTO(1L, "Ana", "Gómez", null);

        when(mapper.mapType(inputDTO, Cliente.class)).thenReturn(clienteEntity);
        when(mapper.mapType(clienteEntity, ClienteDTO.class)).thenReturn(savedDTO);

        mockMvc.perform(post("/cliente/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Ana"));
    }
         @Test
    void testUpdateCliente_success() throws Exception {
        Cliente updatedCliente = new Cliente();
        updatedCliente.setId(1L);
        updatedCliente.setNombre("Carlos");
        updatedCliente.setApellido("Ruiz");

        when(clienteService.modificarCliente(1L, updatedCliente)).thenReturn(updatedCliente);

        mockMvc.perform(put("/cliente/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCliente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Carlos"));
    }
    @Test
    void testUpdateCliente_error() throws Exception {
        Cliente updateRequest = new Cliente();
        updateRequest.setNombre("Carlos");

        doThrow(new RuntimeException("DB error"))
                .when(clienteService).modificarCliente(1L, updateRequest);

        mockMvc.perform(put("/cliente/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.mensaje").value("Error al modificar el cliente"));
    }



    @Test
    void testFinalizarCarritoCliente_success() throws Exception {
        FinalizarCarrito request = new FinalizarCarrito(1L);
        Carrito carrito = new Carrito();
        carrito.setId(100L);

        when(clienteService.finalizarCarritoActivo(1L)).thenReturn(carrito);

        mockMvc.perform(post("/cliente/clientes/finalizar-carrito/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100));
    }

    @Test
    void testEliminarCliente_success() throws Exception {
        mockMvc.perform(delete("/cliente/clientes/1"))
                .andExpect(status().isOk());
    }
}
