package com.backend.nova.Repository;

import com.backend.nova.Entity.Carrito;
import com.backend.nova.Entity.Cliente;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Hidden
@Repository
public interface IClienteRepository extends CrudRepository<Cliente, Long> {

    // Buscar carrito activo (no finalizado) de un cliente
    @Query("SELECT c FROM Carrito c WHERE c.cliente.id = :clienteId AND c.finalizado = false")
    Optional<Carrito> findCarritoNoFinalizadoByClienteId(@Param("clienteId") Long clienteId);

    // Buscar todos los carritos del cliente con sus productos
    @Query("""
            SELECT DISTINCT c
            FROM Carrito c
            JOIN FETCH c.contiene cont
            JOIN FETCH cont.producto p
            WHERE c.cliente.id = :clienteId
            """)
    List<Carrito> findAllCarritoWithProductoByClienteId(@Param("clienteId") Long clienteId);

    // Calcular el total del carrito activo del cliente
    @Query("""
            SELECT SUM(p.precio * cont.cantidad)
            FROM Carrito c
            JOIN c.contiene cont
            JOIN cont.producto p
            WHERE c.cliente.id = :clienteId
            AND c.finalizado = false
            """)
    Double calcularTotalCarritoActivo(@Param("clienteId") Long clienteId);
}
