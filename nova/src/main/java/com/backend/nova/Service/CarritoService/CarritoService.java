package com.backend.nova.Service.CarritoService;

import com.backend.nova.Entity.Carrito;
import com.backend.nova.Entity.Cliente;
import com.backend.nova.Entity.ContieneNM;
import com.backend.nova.Entity.Producto;
import com.backend.nova.Exception.CreateEntityException;
import com.backend.nova.Exception.DeleteEntityException;
import com.backend.nova.Exception.NotFoundEntityException;
import com.backend.nova.Exception.UpdateEntityException;
import com.backend.nova.Repository.ICarritoRepository;
import com.backend.nova.Repository.IClienteRepository;
import com.backend.nova.Repository.IContieneNMRepository;
import com.backend.nova.Repository.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CarritoService implements ICarritoService{

    @Autowired
    private ICarritoRepository iCarritoRepository;

    @Autowired
    private IClienteRepository iClienteRepository;

    @Autowired
    private IProductoRepository iProductoRepository;

    @Autowired
    private IContieneNMRepository iContieneNMRepository;

    @Override
    public List<Carrito> findAll() {
        return (List<Carrito>) iCarritoRepository.findAll();
    }

    @Override
    public Carrito findById(long id) {
        return iCarritoRepository.findById(id).orElseThrow(()
        -> new NotFoundEntityException(id, Carrito.class.getSimpleName()));
    }

    @Override
    public Carrito crearCarrito(Carrito carrito) {
        try{
            return iCarritoRepository.save(carrito);
        }catch (Exception e){
            throw new CreateEntityException(carrito, e);
        }
    }

    @Override
    public Carrito modificarCarrito(long id, Carrito carrito) {

        /*
        aqui hay que establecer algun set / get para modificar el carrito?
         */
        try {
            Carrito newCarrito = iCarritoRepository.findById(id).orElseThrow(
                    () -> new NotFoundEntityException(id, Carrito.class.getSimpleName())
            );
            newCarrito.setCliente(carrito.getCliente());

            return iCarritoRepository.save(newCarrito);
        } catch (Exception e) {
            throw new UpdateEntityException("Error al modificar el carrito con id: " + id, e);
        }
    }

    @Override
    public boolean deleteById(long id) throws Exception {
        try{
            iCarritoRepository.findById(id).orElseThrow(()
                    -> new NotFoundEntityException(id, Carrito.class.getSimpleName()));
            iCarritoRepository.deleteById(id);
            return true;
        }catch (Exception e){
            throw new DeleteEntityException(id, Carrito.class.getSimpleName(), e);
        }
    }

    /**
     * Método para obtener los carritos finalizados de un cliente
     */
    @Override
    public List<Carrito> findFinalizedCarritosByCliente(Long clienteId) {
        return iCarritoRepository.findFinalizedCarritosByCliente(clienteId);
    }

    /**
     * Método para agregar un producto al carrito del cliente
     */
    public Carrito agregarProductoAlCarrito(Long clienteId, Long productoId, int cantidad) {
        //Busca cliente
        Cliente cliente = iClienteRepository.findById(clienteId)
                .orElseThrow(() -> new NotFoundEntityException(clienteId, Cliente.class.getSimpleName()));

        //Busca producto
        Producto producto = iProductoRepository.findById(productoId)
                .orElseThrow(() -> new NotFoundEntityException(productoId, Producto.class.getSimpleName()));

        //Busca carrito  del cliente
        Carrito carrito = iCarritoRepository.findOpenCartByCliente(clienteId)
                .orElseGet(() -> {
                    // Si no existe carrito abierto, crear uno nuevo
                    Carrito newCart = new Carrito();
                    newCart.setCliente(cliente);
                    newCart.setFechaCompra(LocalDateTime.now());
                    newCart.setFinalizado(false);
                    return iCarritoRepository.save(newCart);
                });

        //Crea ContieneNM (relación carrito-producto)
        ContieneNM contieneNM = new ContieneNM();
        contieneNM.setCarrito(carrito);
        contieneNM.setCantidad(cantidad);
        contieneNM = iContieneNMRepository.save(contieneNM);

        //Asociar el producto a la relación
        producto.setContiene(contieneNM);
        iProductoRepository.save(producto);

        //Retornar el carrito actualizado
        return carrito;
    }

}
