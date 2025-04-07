package com.backend.nova.Service.ProductoService;

import com.backend.nova.Entity.Producto;
import com.backend.nova.Exception.CreateEntityException;
import com.backend.nova.Exception.DeleteEntityException;
import com.backend.nova.Exception.NotFoundEntityException;
import com.backend.nova.Repository.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService implements IProductoService{

    @Autowired
    private IProductoRepository iProductoRepository;

    @Override
    public List<Producto> findAll() {
        return (List<Producto>) iProductoRepository.findAll();
    }

    @Override
    public Producto findById(Long id) {
        return iProductoRepository.findById(id).orElseThrow(()
                -> new NotFoundEntityException(id, Producto.class.getSimpleName()));
    }

    @Override
    public Producto crearProducto(Producto producto) {
        try{
            return iProductoRepository.save(producto);
        }catch (Exception e){
            throw new CreateEntityException(producto, e);
        }
    }

    @Override
    public Producto modificarProducto(Long id, Producto producto) {
        Producto newProducto = iProductoRepository.findById(id).orElseThrow(
                () -> new NotFoundEntityException(id, Producto.class.getSimpleName())
        );
        newProducto.setNombre(producto.getNombre());
        newProducto.setPrecio(producto.getPrecio());
        try{
            return iProductoRepository.save(newProducto);
        }catch (Exception e){
            throw new CreateEntityException(producto, e);
        }
    }

    @Override
    public boolean deleteById(Long id) throws Exception {
        try{
            iProductoRepository.findById(id).orElseThrow(
                    () -> new NotFoundEntityException(id, Producto.class.getSimpleName())
            );
            iProductoRepository.deleteById(id);
            return true;
        }catch (Exception e){
            throw new DeleteEntityException(id, Producto.class.getSimpleName(), e);
        }
    }

    @Override
    public List<Producto> findProductosNoEntregados() {
        return iProductoRepository.findByEntregadoFalse();
    }
}
