package com.backend.nova.DTO.DistribuidorDTO;

import com.backend.nova.DTO.ProductoDTO.ProductoDTO;
import com.backend.nova.DTO.ProveedorDTO.ProveedorDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DistribuidorDTO implements Serializable {
    private long id;
    private String nombre;
    private String nif;
    private String direccion;
    private String telefono;
    private String email;
    private String nombreRepartidor;
    private ProveedorDTO proveedor;
}
