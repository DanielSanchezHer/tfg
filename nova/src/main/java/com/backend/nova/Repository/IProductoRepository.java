package com.backend.nova.Repository;

import com.backend.nova.Entity.Producto;
import org.springframework.data.repository.CrudRepository;

public interface IProductoRepository extends CrudRepository<Producto, Long> {
}
