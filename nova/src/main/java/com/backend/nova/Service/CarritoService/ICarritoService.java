package com.backend.nova.Service.CarritoService;

import com.backend.nova.Entity.Carrito;
import com.backend.nova.Entity.ContieneNM;

import java.util.List;

public interface ICarritoService {
    List<Carrito> findAll();
    Carrito findById(long id);
    Carrito crearCarrito(Carrito carrito);
    Carrito modificarCarrito(long id,Carrito carrito);
    boolean deleteById(long id) throws Exception;
    List<Carrito> findFinalizedCarritosByCliente(Long clienteId);
    Carrito agregarProductoAlCarrito(Long clienteId, Long productoId, int cantidad);
    Carrito getCarritoAbiertoPorCliente(Long clienteId);
    List<ContieneNM> getProductosPorCarrito(Long carritoId);
}
