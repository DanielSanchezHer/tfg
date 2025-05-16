package com.backend.nova.TestCarritoController;

import com.backend.nova.Controller.DistribuidorController;
import com.backend.nova.DTO.DistribuidorDTO.DistribuidorDTO;
import com.backend.nova.Entity.Distribuidor;
import com.backend.nova.Mapper.Mapper;
import com.backend.nova.Service.DistribuidorService.DistribuidorService;
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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class DistribuidorControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DistribuidorService distribuidorService;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private DistribuidorController distribuidorController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Distribuidor distribuidor;
    private DistribuidorDTO distribuidorDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(distribuidorController).build();

        distribuidor = new Distribuidor();
        distribuidor.setId(1L);
        distribuidor.setNombre("DistribuidorTest");
        distribuidor.setNif("ABC123");

        distribuidorDTO = new DistribuidorDTO();
        distribuidorDTO.setId(1L);
        distribuidorDTO.setNombre("DistribuidorTest");
        distribuidorDTO.setNif("ABC123");
    }

    @Test
    void getAllDistribuidores_ReturnsList() throws Exception {
        when(distribuidorService.findAll()).thenReturn(Collections.singletonList(distribuidor));
        when(mapper.mapList(anyList(), eq(DistribuidorDTO.class))).thenReturn(Collections.singletonList(distribuidorDTO));

        mockMvc.perform(get("/distribuidor/distribuidores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("DistribuidorTest"));
    }

    @Test
    void getDistribuidorById_ReturnsDistribuidor() throws Exception {
        when(distribuidorService.findById(1L)).thenReturn(distribuidor);
        when(mapper.mapType(any(Distribuidor.class), eq(DistribuidorDTO.class))).thenReturn(distribuidorDTO);

        mockMvc.perform(get("/distribuidor/distribuidores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("DistribuidorTest"));
    }

    @Test
    void crearDistribuidor_ReturnsCreatedDistribuidor() throws Exception {
        when(mapper.mapType(any(DistribuidorDTO.class), eq(Distribuidor.class))).thenReturn(distribuidor);
        when(distribuidorService.crearDistribuidor(any(Distribuidor.class))).thenReturn(distribuidor);
        when(mapper.mapType(any(Distribuidor.class), eq(DistribuidorDTO.class))).thenReturn(distribuidorDTO);

        mockMvc.perform(post("/distribuidor/distribuidores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(distribuidorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("DistribuidorTest"));
    }
}