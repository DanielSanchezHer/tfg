package com.backend.nova.DTO.CarritoDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AnyadirProductoCarrito implements Serializable {
    private Long clienteId;
    private Long productoId;
    private int cantidad;
}
