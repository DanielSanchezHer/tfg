package com.backend.nova.TestController;


import com.backend.nova.Controller.AuthenticationController;
import com.backend.nova.Controller.DistribuidorController;
import com.backend.nova.DTO.DistribuidorDTO.DistribuidorDTO;
import com.backend.nova.DTO.UsuarioDTO.CrearUsuarioDTO;
import com.backend.nova.DTO.UsuarioDTO.LoginUsuarioDTO;
import com.backend.nova.Entity.Cliente;
import com.backend.nova.Entity.Distribuidor;
import com.backend.nova.Entity.Role;
import com.backend.nova.Entity.RoleType;
import com.backend.nova.Mapper.Mapper;
import com.backend.nova.Repository.IRoleRepository;
import com.backend.nova.Service.DistribuidorService.DistribuidorService;
import com.backend.nova.Service.authentication.IAutenticationService;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
public class TestAuthenticationController {

    private MockMvc mockMvc;

    @Mock
    private IAutenticationService authenticationService;

    @Mock
    private Mapper mapper;

    @Mock
    private IRoleRepository roleRepository;

    @InjectMocks
    private AuthenticationController authenticationController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
    }

       @Test
          void testRegisterClienteSuccess() throws Exception {
              CrearUsuarioDTO crearUsuarioDTO = new CrearUsuarioDTO();
              crearUsuarioDTO.setUsername("cliente1");
              crearUsuarioDTO.setPassword("clave123");
              crearUsuarioDTO.setEmail("cliente1@example.com");
              crearUsuarioDTO.setRoles(List.of(1));
             crearUsuarioDTO.setNombre("Nombre");
              crearUsuarioDTO.setApellido("Apellido");
              crearUsuarioDTO.setDni("99999999X");

              Cliente clienteMock = new Cliente();
              clienteMock.setUsername(crearUsuarioDTO.getUsername());

              Role roleMock = new Role();
              roleMock.setId(1);
              roleMock.setNombre(RoleType.CLIENTE);

              when(mapper.mapType(any(), any())).thenReturn(clienteMock);
              when(roleRepository.findByNombreIn(any())).thenReturn(List.of(roleMock));
              when(authenticationService.signup(any())).thenReturn(clienteMock);

              mockMvc.perform(post("/auth/signup/cliente")
                              .contentType(MediaType.APPLICATION_JSON)
                              .content(objectMapper.writeValueAsString(crearUsuarioDTO)))
                      .andExpect(status().isOk())
                      .andExpect(jsonPath("$.mensaje").value("Usuario registrado correctamente"));
          }
       @Test
          void testLoginSuccess() throws Exception {
              LoginUsuarioDTO loginDTO = new LoginUsuarioDTO("cliente1", "clave123");

              Cliente clienteMock = new Cliente();
              clienteMock.setUsername("cliente1");

              when(mapper.mapType(any(), any())).thenReturn(clienteMock);
              when(authenticationService.authenticate(any())).thenReturn("fake-jwt-token");

              mockMvc.perform(post("/auth/login")
                              .contentType(MediaType.APPLICATION_JSON)
                              .content(objectMapper.writeValueAsString(loginDTO)))
                      .andExpect(status().isOk())
                      .andExpect(content().string("fake-jwt-token"));
          }


       @Test
          void testLoginFailure() throws Exception {
              LoginUsuarioDTO loginDTO = new LoginUsuarioDTO("cliente1", "clave_incorrecta");

              Cliente clienteMock = new Cliente();
              clienteMock.setUsername("cliente1");

              when(mapper.mapType(any(), any())).thenReturn(clienteMock);
              when(authenticationService.authenticate(any()))
                      .thenThrow(new RuntimeException("Credenciales inválidas"));

              mockMvc.perform(post("/auth/login")
                              .contentType(MediaType.APPLICATION_JSON)
                              .content(objectMapper.writeValueAsString(loginDTO)))
                      .andExpect(status().isUnauthorized())
                     .andExpect(jsonPath("$.mensaje").value("Credenciales inválidas"));
          }

}
