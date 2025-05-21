package com.backend.nova.TestController;

import com.backend.nova.Config.Security.JwtTokenProvider;
import com.backend.nova.Controller.AuthenticationController;
import com.backend.nova.Controller.CarritoController;
import com.backend.nova.DTO.CarritoDTO.AnyadirProductoCarrito;
import com.backend.nova.DTO.CarritoDTO.CarritoDTO;
import com.backend.nova.DTO.ProductoDTO.ProductoCarritoDTO;
import com.backend.nova.Entity.Carrito;
import com.backend.nova.Entity.ContieneNM;
import com.backend.nova.Exception.NotFoundEntityException;
import com.backend.nova.Mapper.Mapper;
import com.backend.nova.Service.CarritoService.CarritoService;
import com.backend.nova.Service.UsuarioService.IUsuarioService;
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

import java.util.List;
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
public class TestCarritoController {

    private MockMvc mockMvc;

    @Mock
    private CarritoService carritoService;
    @InjectMocks
    private CarritoController carritoController;

    @Mock
    private Mapper mapper;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private IUsuarioService usuarioService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    @InjectMocks
    private AuthenticationController authenticationController;
    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.standaloneSetup(carritoController).build(); // Usa carritoController
    }

    // Test para obtener todos los carritos
    @Test
    public void testGetAllCarritos() throws Exception {
        Carrito carrito = new Carrito();
        carrito.setId(1L);
        CarritoDTO carritoDTO = new CarritoDTO();
        carritoDTO.setId(1L);

        when(carritoService.findAll()).thenReturn(List.of(carrito));
        when(mapper.mapList(any(), eq(CarritoDTO.class))).thenReturn(List.of(carritoDTO));

        mockMvc.perform(get("/carrito/carritos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));

        verify(carritoService, times(1)).findAll();
        verify(mapper, times(1)).mapList(any(), eq(CarritoDTO.class));
    }

    // Test para obtener carrito por ID
    @Test
    public void testGetCarritoById() throws Exception {
        Carrito carrito = new Carrito();
        carrito.setId(1L);
        CarritoDTO carritoDTO = new CarritoDTO();
        carritoDTO.setId(1L);

        when(carritoService.findById(1L)).thenReturn(carrito);
        when(mapper.mapType(any(), eq(CarritoDTO.class))).thenReturn(carritoDTO);

        mockMvc.perform(get("/carrito/carritos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(carritoService, times(1)).findById(1L);
        verify(mapper, times(1)).mapType(any(), eq(CarritoDTO.class));
    }

    // Test para crear carrito
    @Test
    public void testCreateCarrito() throws Exception {
        CarritoDTO requestDTO = new CarritoDTO();
        Carrito carrito = new Carrito();
        carrito.setId(1L);
        CarritoDTO responseDTO = new CarritoDTO();
        responseDTO.setId(1L);

        when(mapper.mapType(any(CarritoDTO.class), eq(Carrito.class))).thenReturn(carrito);
        when(carritoService.crearCarrito(any(Carrito.class))).thenReturn(carrito);
        when(mapper.mapType(any(Carrito.class), eq(CarritoDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/carrito/carritos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(carritoService, times(1)).crearCarrito(any(Carrito.class));
        verify(mapper, times(1)).mapType(any(CarritoDTO.class), eq(Carrito.class));
        verify(mapper, times(1)).mapType(any(Carrito.class), eq(CarritoDTO.class));
    }

    // Test para actualizar carrito
    @Test
    public void testUpdateCarrito() throws Exception {
        Carrito carrito = new Carrito();
        carrito.setId(1L);

        when(carritoService.modificarCarrito(eq(1L), any(Carrito.class))).thenReturn(carrito);

        mockMvc.perform(put("/carrito/carritos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carrito)))
                .andExpect(status().isOk());

        verify(carritoService, times(1)).modificarCarrito(eq(1L), any(Carrito.class));
    }

    // Test para eliminar carrito
    /*

     */
    @Test
    public void testDeleteCarrito() throws Exception {
        mockMvc.perform(delete("/carrito/carritos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carrito").value("Carrito eliminado"));

        verify(carritoService, times(1)).deleteById(1L);
    }

    // Test para agregar producto al carrito
    @Test
    public void testAgregarProductoAlCarrito() throws Exception {
        AnyadirProductoCarrito request = new AnyadirProductoCarrito(1L, 2L, 3);
        Carrito carrito = new Carrito();
        carrito.setId(1L);
        CarritoDTO carritoDTO = new CarritoDTO();
        carritoDTO.setId(1L);

        when(carritoService.agregarProductoAlCarrito(1L, 2L, 3)).thenReturn(carrito);
        when(mapper.mapType(any(Carrito.class), eq(CarritoDTO.class))).thenReturn(carritoDTO);

        mockMvc.perform(post("/carrito/carritos/agregar-producto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(carritoService, times(1)).agregarProductoAlCarrito(1L, 2L, 3);
        verify(mapper, times(1)).mapType(any(Carrito.class), eq(CarritoDTO.class));
    }

    // Test para obtener carritos finalizados por cliente
    @Test
    public void testGetCarritosFinalizadosByCliente() throws Exception {
        Carrito carrito = new Carrito();
        carrito.setId(1L);
        CarritoDTO carritoDTO = new CarritoDTO();
        carritoDTO.setId(1L);

        when(carritoService.findFinalizedCarritosByCliente(1L)).thenReturn(List.of(carrito));
        when(mapper.mapList(any(), eq(CarritoDTO.class))).thenReturn(List.of(carritoDTO));

        mockMvc.perform(get("/carrito/carritos/cliente/1/finalizados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));

        verify(carritoService, times(1)).findFinalizedCarritosByCliente(1L);
        verify(mapper, times(1)).mapList(any(), eq(CarritoDTO.class));
    }

    // Test para obtener carrito no finalizado
    @Test
    public void testGetCarritoNoFinalizado() throws Exception {
        Carrito carrito = new Carrito();
        carrito.setId(1L);
        CarritoDTO carritoDTO = new CarritoDTO();
        carritoDTO.setId(1L);

        when(carritoService.getCarritoAbiertoPorCliente(1L)).thenReturn(carrito);
        when(mapper.mapType(any(), eq(CarritoDTO.class))).thenReturn(carritoDTO);

        mockMvc.perform(get("/carrito/carritos/carritosNoFinalizados")
                        .param("clienteId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(carritoService, times(1)).getCarritoAbiertoPorCliente(1L);
        verify(mapper, times(1)).mapType(any(), eq(CarritoDTO.class));
    }

    // Test para obtener productos de carrito
    @Test
    public void testGetProductosDeCarrito() throws Exception {
        ContieneNM contiene = new ContieneNM();
        ProductoCarritoDTO productoDTO = new ProductoCarritoDTO();

        when(carritoService.getProductosPorCarrito(1L)).thenReturn(List.of(contiene));
        when(mapper.mapList(any(), eq(ProductoCarritoDTO.class))).thenReturn(List.of(productoDTO));

        mockMvc.perform(get("/carrito/carritos/1/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists());

        verify(carritoService, times(1)).getProductosPorCarrito(1L);
        verify(mapper, times(1)).mapList(any(), eq(ProductoCarritoDTO.class));
    }

    // Test para caso de error al obtener carrito por ID
    /*
    @Test
    public void testGetCarritoByIdNotFound() throws Exception {
        when(carritoService.findById(1L))
                .thenThrow(new NotFoundEntityException(1L, "Carrito"));

        mockMvc.perform(get("/carrito/carritos/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
    }
     */


    // Test para caso de error al eliminar carrito
    @Test
    public void testDeleteCarritoNotFound() throws Exception {
        doThrow(new NotFoundEntityException(1L, "Carrito"))
                .when(carritoService).deleteById(1L);

        mockMvc.perform(delete("/carrito/carritos/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
    }
}