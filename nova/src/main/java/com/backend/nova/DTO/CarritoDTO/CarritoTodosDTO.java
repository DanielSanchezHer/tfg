package com.backend.nova.DTO.CarritoDTO;

import com.backend.nova.Entity.Cliente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CarritoTodosDTO implements Serializable {
        private long id;
        private LocalDateTime fechaCompra;
        private boolean finalizado;
}
