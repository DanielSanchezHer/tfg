package com.backend.nova.TestCarritoController;

import com.backend.nova.Controller.ProductoController;
import com.backend.nova.DTO.ProductoDTO.CrearProductoDTO;
import com.backend.nova.DTO.ProductoDTO.ProductoDTO;
import com.backend.nova.Entity.Producto;
import com.backend.nova.Mapper.Mapper;
import com.backend.nova.Service.ProductoService.ProductoService;
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
public class ProductoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductoService productoService;

    @Mock
    private Mapper productoMapper;

    @InjectMocks
    private ProductoController productoController;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Producto producto;
    private ProductoDTO productoDTO;
    private CrearProductoDTO crearProductoDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productoController).build();

        producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Test Producto");
        producto.setDescripcion("Descripción");
        producto.setPrecio(20.5);
        producto.setEntregado(false);

        productoDTO = new ProductoDTO();
        productoDTO.setId(1L);
        productoDTO.setNombre("Test Producto");
        productoDTO.setDescripcion("Descripción");
        productoDTO.setPrecio(20.5);
        productoDTO.setEntregado(false);

        crearProductoDTO = new CrearProductoDTO();
        crearProductoDTO.setId(1L);
        crearProductoDTO.setNombre("Test Producto");
        crearProductoDTO.setDescripcion("Descripción");
        crearProductoDTO.setPrecio(20.5);
        crearProductoDTO.setEntregado(false);
    }

    @Test
    void getAllProductos_ReturnsList() throws Exception {
        when(productoService.findAll()).thenReturn(Collections.singletonList(producto));
        when(productoMapper.mapList(anyList(), eq(ProductoDTO.class)))
                .thenReturn(Collections.singletonList(productoDTO));

        mockMvc.perform(get("/producto/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Test Producto"));
    }

    @Test
    void getProductoById_ReturnsProducto() throws Exception {
        when(productoService.findById(1L)).thenReturn(producto);
        when(productoMapper.mapType(producto, ProductoDTO.class)).thenReturn(productoDTO);

        mockMvc.perform(get("/producto/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Test Producto"));
    }

    @Test
    void crearProducto_ReturnsCreatedProducto() throws Exception {
        when(productoMapper.mapType(crearProductoDTO, Producto.class)).thenReturn(producto);
        when(productoService.crearProducto(producto)).thenReturn(producto);
        when(productoMapper.mapType(producto, ProductoDTO.class)).thenReturn(productoDTO);

        mockMvc.perform(post("/producto/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(crearProductoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Test Producto"));
    }

    @Test
    void modificarProducto_ReturnsUpdatedProducto() throws Exception {
        when(productoService.modificarProducto(eq(1L), any(Producto.class))).thenReturn(producto);

        mockMvc.perform(put("/producto/productos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Test Producto"));
    }

    @Test
    void eliminarProducto_ReturnsSuccessMessage() throws Exception {
        when(productoService.deleteById(1L)).thenReturn(true);

        mockMvc.perform(delete("/producto/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").value("Producto eliminado"));
    }

    @Test
    void getProductosNoEntregados_ReturnsList() throws Exception {
        when(productoService.findProductosNoEntregados()).thenReturn(Collections.singletonList(producto));
        when(productoMapper.mapList(anyList(), eq(ProductoDTO.class)))
                .thenReturn(Collections.singletonList(productoDTO));

        mockMvc.perform(get("/producto/productos/no-entregados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].entregado").value(false));
    }
}
