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
    private Long productoId;
    private String nombreProducto;
    private int cantidad;
    private double precio;
}
