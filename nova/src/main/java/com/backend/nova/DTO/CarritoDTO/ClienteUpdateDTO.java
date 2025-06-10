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
public class ClienteUpdateDTO implements Serializable {
    private String dni;
    private String email;
    private String nombre;
    private String apellido;
}
