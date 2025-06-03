package com.backend.nova.Repository;

import com.backend.nova.Entity.Producto;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Hidden
@Repository
public interface IProductoRepository extends CrudRepository<Producto, Long> {
    List<Producto> findByEntregadoFalse();
    @Query("SELECT p FROM Producto p WHERE p.distribuidor.proveedor.nombre = :nombreProveedor")
    List<Producto> findProductosByNombreProveedor(String nombreProveedor);
    @Query("SELECT p FROM Producto p WHERE LOWER(p.distribuidor.nombre) = LOWER(:nombreDistribuidor)")
    List<Producto> findProductosByNombreDistribuidor(String nombreDistribuidor);
}
