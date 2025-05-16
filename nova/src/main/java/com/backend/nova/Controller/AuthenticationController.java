package com.backend.nova.Controller;


import com.backend.nova.DTO.UsuarioDTO.CrearUsuarioDTO;
import com.backend.nova.DTO.UsuarioDTO.LoginUsuarioDTO;
import com.backend.nova.Entity.Cliente;
import com.backend.nova.Entity.Role;
import com.backend.nova.Exception.Response;
import com.backend.nova.Mapper.Mapper;
import com.backend.nova.Repository.IRoleRepository;
import com.backend.nova.Service.authentication.IAutenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"*"})
@Tag(name = "Autentificacion", description = "Gestión de acceso y autentificacion")
public class AuthenticationController {

    @Autowired
    private IAutenticationService authenticationService;

    @Autowired
    private Mapper mapper;

    @Autowired
    private IRoleRepository roleRepository;

    @PostMapping("/signup/cliente")
    public ResponseEntity<?> registerCliente(@RequestBody CrearUsuarioDTO registerUsuarioDto)
    {
        Cliente user = mapper.mapType(registerUsuarioDto, Cliente.class);

        List<Role> roles = roleRepository.findByNombreIn(registerUsuarioDto.getRoles());

        user.setRoles(roles);

        authenticationService.signup(user);

        String message = "Usuario registrado correctamente";

        return new ResponseEntity<Response>(Response.Ok(message), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody LoginUsuarioDTO loginUserDto)
    {

        Cliente loginUser = mapper.mapType(loginUserDto, Cliente.class);

        String jwtToken = authenticationService.authenticate(loginUser);

        return ResponseEntity.ok(jwtToken);
    }

}