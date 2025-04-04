package com.backend.nova.DTO.ProveedorDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CrearProveedorDTO implements Serializable {
    private String nombre;
    private String nif;
    private String direccion;
    private String telefono;
    private String email;
}
