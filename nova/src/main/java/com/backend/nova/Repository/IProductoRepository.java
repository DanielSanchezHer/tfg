package com.backend.nova.Repository;

import com.backend.nova.Entity.Producto;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Hidden
@Repository
public interface IProductoRepository extends CrudRepository<Producto, Long> {
    List<Producto> findByEntregadoFalse();
}
