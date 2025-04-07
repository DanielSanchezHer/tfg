package com.backend.nova.Service.ProductoService;

import com.backend.nova.Entity.Producto;

import java.util.List;

public interface IProductoService {
    List<Producto> findAll();
    Producto findById(Long id);
    Producto crearProducto(Producto producto);
    Producto modificarProducto(Long id,Producto producto);
    boolean deleteById(Long id) throws Exception;
    List<Producto> findProductosNoEntregados();
}
