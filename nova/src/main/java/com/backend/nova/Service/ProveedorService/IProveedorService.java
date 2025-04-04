package com.backend.nova.Service.ProveedorService;

import com.backend.nova.Entity.Proveedor;

import java.util.List;

public interface IProveedorService {
    List<Proveedor> findAll();
    Proveedor findById(Long id);
    Proveedor crearProveedor(Proveedor proveedor);
    Proveedor modificarProveedor(Long id,Proveedor proveedor);
    boolean deleteById(Long id) throws Exception;
}
