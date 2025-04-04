package com.backend.nova.Repository;

import com.backend.nova.Entity.Carrito;
import com.backend.nova.Entity.Cliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IClienteRepository extends CrudRepository<Cliente, Long> {
    @Query("SELECT c FROM Carrito c WHERE c.cliente.id = :clienteId AND c.finalizado = false")
    Optional<Carrito> findCarritoNoFinalizadoByClienteId(@Param("clienteId") Long clienteId);
}
