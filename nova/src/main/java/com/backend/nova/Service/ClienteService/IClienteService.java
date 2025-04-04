package com.backend.nova.Service.ClienteService;

import com.backend.nova.Entity.Carrito;
import com.backend.nova.Entity.Cliente;

import java.util.List;

public interface IClienteService {
    List<Cliente> findAll();
    Cliente findById(Long id);
    Cliente crearCliente(Cliente cliente);
    Cliente modificarCliente(Long id,Cliente cliente);
    boolean deleteById(Long id) throws Exception;
    Carrito finalizarCarritoActivo(Long clienteId);
}
