package com.backend.nova.Service.ProveedorService;

import com.backend.nova.Entity.Proveedor;
import com.backend.nova.Exception.CreateEntityException;
import com.backend.nova.Exception.DeleteEntityException;
import com.backend.nova.Exception.NotFoundEntityException;
import com.backend.nova.Exception.UpdateEntityException;
import com.backend.nova.Repository.IProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorService implements IProveedorService {

    @Autowired
    private IProveedorRepository iProveedorRepository;

    @Override
    public List<Proveedor> findAll() {
        return (List<Proveedor>) iProveedorRepository.findAll();
    }

    @Override
    public Proveedor findById(Long id) {
        return iProveedorRepository.findById(id).orElseThrow(()
                -> new NotFoundEntityException(id, Proveedor.class.getSimpleName()));
    }

    @Override
    public Proveedor crearProveedor(Proveedor proveedor) {
        try{
            return iProveedorRepository.save(proveedor);
        }catch (Exception e){
            throw new CreateEntityException(proveedor, e);
        }
    }

    @Override
    public Proveedor modificarProveedor(Long id, Proveedor proveedor) {
        Proveedor newProveedor = iProveedorRepository.findById(id).orElseThrow(
                () -> new NotFoundEntityException(id, Proveedor.class.getSimpleName())
        );
        newProveedor.setNombre(proveedor.getNombre());
        newProveedor.setDireccion(proveedor.getDireccion());
        try{
            return iProveedorRepository.save(newProveedor);
        }catch (Exception e){
            throw new UpdateEntityException(proveedor, e);
        }
    }

    @Override
    public boolean deleteById(Long id) throws Exception {
        try{
            iProveedorRepository.findById(id).orElseThrow(
                    () -> new NotFoundEntityException(id, Proveedor.class.getSimpleName())
            );
            iProveedorRepository.deleteById(id);
            return true;
        }catch (Exception e){
            throw new DeleteEntityException(id, Proveedor.class.getSimpleName(), e);
        }
    }
}
