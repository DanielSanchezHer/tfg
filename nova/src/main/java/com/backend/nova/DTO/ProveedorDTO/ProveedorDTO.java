package com.backend.nova.DTO.ProveedorDTO;

import com.backend.nova.DTO.DistribuidorDTO.DistribuidorDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProveedorDTO implements Serializable {
    private long id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;
    //private DistribuidorDTO distribuidor;
}
