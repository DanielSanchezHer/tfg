package com.backend.nova.DTO.UsuarioDTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CrearUsuarioDTO implements Serializable {
    @NotNull
    public String username;
    @NotNull
    public String password;
    public String dni;
    @NotNull
    public String email;
    public List<Integer> roles;
    public String nombre;
    public String apellido;
}
