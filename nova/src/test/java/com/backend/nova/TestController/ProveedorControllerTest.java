package com.backend.nova.TestController;

import com.backend.nova.Controller.ProveedorController;
import com.backend.nova.DTO.ProveedorDTO.ProveedorDTO;
import com.backend.nova.Entity.Proveedor;
import com.backend.nova.Mapper.Mapper;
import com.backend.nova.Service.ProveedorService.ProveedorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ProveedorControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProveedorService proveedorService;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private ProveedorController proveedorController;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Proveedor proveedor;
    private ProveedorDTO proveedorDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(proveedorController).build();

        proveedor = new Proveedor();
        proveedor.setId(1L);
        proveedor.setNombre("Proveedor Test");
        proveedor.setDireccion("Dirección 123");
        proveedor.setTelefono("123456789");
        proveedor.setEmail("proveedor@test.com");

        proveedorDTO = new ProveedorDTO();
        proveedorDTO.setId(1L);
        proveedorDTO.setNombre("Proveedor Test");
        proveedorDTO.setDireccion("Dirección 123");
        proveedorDTO.setTelefono("123456789");
        proveedorDTO.setEmail("proveedor@test.com");
    }

    @Test
    void getAllProveedores_ReturnsList() throws Exception {
        when(proveedorService.findAll()).thenReturn(Collections.singletonList(proveedor));
        when(mapper.mapList(anyList(), eq(ProveedorDTO.class))).thenReturn(Collections.singletonList(proveedorDTO));

        mockMvc.perform(get("/proveedor/proveedores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Proveedor Test"));
    }

    @Test
    void getProveedorById_ReturnsProveedor() throws Exception {
        when(proveedorService.findById(1L)).thenReturn(proveedor);
        when(mapper.mapType(proveedor, ProveedorDTO.class)).thenReturn(proveedorDTO);

        mockMvc.perform(get("/proveedor/proveedores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Proveedor Test"));
    }
    /*
         @Test
    void crearProveedor_ReturnsCreatedProveedor() throws Exception {
        when(mapper.mapType(proveedorDTO, Proveedor.class)).thenReturn(proveedor);
        when(proveedorService.crearProveedor(proveedor)).thenReturn(proveedor);
        when(mapper.mapType(proveedor, ProveedorDTO.class)).thenReturn(proveedorDTO);

        mockMvc.perform(post("/proveedor/proveedores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(proveedorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Proveedor Test"));
    }
     */


    @Test
    void modificarProveedor_ReturnsUpdatedProveedor() throws Exception {
        when(proveedorService.modificarProveedor(eq(1L), any(Proveedor.class))).thenReturn(proveedor);

        mockMvc.perform(put("/proveedor/proveedores/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(proveedor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Proveedor Test"));
    }

    @Test
    void eliminarProveedor_ReturnsSuccessMessage() throws Exception {
        when(proveedorService.deleteById(1L)).thenReturn(true);

        mockMvc.perform(delete("/proveedor/proveedores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.proveedor ").value("Proveedor eliminado"));
    }
}
