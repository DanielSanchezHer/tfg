package com.backend.nova.DTO.ClienteDTO;

import com.backend.nova.DTO.CarritoDTO.CarritoDTO;
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
public class ClienteDTO implements Serializable {
    private long id;
    private String nombre;
    private String apellido;
    private List<CarritoDTO> carritos;
}
