package com.backend.nova.DTO.ContieneNMDTO;

import com.backend.nova.DTO.CarritoDTO.CarritoDTO;
import com.backend.nova.DTO.ProductoDTO.ProductoDTO;
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
public class ContieneNMDTO implements Serializable {
    private long id;
    private int cantidad;
    private CarritoDTO carrito;
    private List<ProductoDTO> productos;
}
