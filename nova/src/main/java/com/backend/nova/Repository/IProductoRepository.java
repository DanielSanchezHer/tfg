package com.backend.nova.Repository;

import com.backend.nova.Entity.Producto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IProductoRepository extends CrudRepository<Producto, Long> {
    List<Producto> findByEntregadoFalse();
}
