package com.backend.nova.DTO.ContieneNMDTO;

import com.backend.nova.DTO.CarritoDTO.CarritoDTO;
import com.backend.nova.DTO.ProductoDTO.ProductoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CrearContieneNMDTO implements Serializable {
    private int cantidad;
    private CarritoDTO carrito;
    private ProductoDTO producto;
}
