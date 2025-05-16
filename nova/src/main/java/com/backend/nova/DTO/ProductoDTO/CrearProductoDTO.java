package com.backend.nova.DTO.ProductoDTO;

import com.backend.nova.DTO.ContieneNMDTO.ContieneNMDTO;
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
public class CrearProductoDTO implements Serializable {
    private long id;
    private String nombre;
    private String descripcion;
    private double precio;
    private boolean entregado;
}
