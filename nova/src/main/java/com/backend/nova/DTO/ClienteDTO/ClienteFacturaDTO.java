package com.backend.nova.DTO.ClienteDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClienteFacturaDTO implements Serializable {
        //private long idCliente;
        private double totalFactura;
}
