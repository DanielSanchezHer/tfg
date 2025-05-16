package com.backend.nova.DTO.CarritoDTO;

import com.backend.nova.DTO.ContieneNMDTO.ContieneNMDTO;
import com.backend.nova.Entity.Cliente;
import com.backend.nova.Entity.ContieneNM;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CarritoDTO implements Serializable {
    private long id;
    private LocalDateTime fechaCompra;
    private boolean finalizado;
    private Cliente cliente;
}
