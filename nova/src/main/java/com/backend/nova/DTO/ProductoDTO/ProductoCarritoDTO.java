package com.backend.nova.DTO.ProductoDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductoCarritoDTO {
    private long id;
    private String nombre;
    private String descripcion;
    private double precio;
    private boolean entregado;
}
