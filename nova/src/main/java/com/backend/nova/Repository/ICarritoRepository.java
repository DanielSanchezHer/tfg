package com.backend.nova.Repository;

import com.backend.nova.Entity.Carrito;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Hidden
@Repository
public interface ICarritoRepository extends CrudRepository<Carrito, Long> {

    @Query("SELECT c FROM Carrito c WHERE c.cliente.id = :clienteId AND c.finalizado = true")
    List<Carrito> findFinalizedCarritosByCliente(@Param("clienteId") Long clienteId);

    @Query("SELECT c FROM Carrito c LEFT JOIN FETCH c.contiene WHERE c.cliente.id = :clienteId AND c.finalizado = false")
    Optional<Carrito> findOpenCartByCliente(@Param("clienteId") Long clienteId);
}