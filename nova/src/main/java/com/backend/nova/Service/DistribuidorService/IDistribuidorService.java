package com.backend.nova.Service.DistribuidorService;

import com.backend.nova.Entity.Distribuidor;

import java.util.List;

public interface IDistribuidorService {
    List<Distribuidor> findAll();
    Distribuidor findById(Long id);
    Distribuidor crearDistribuidor(Distribuidor distribuidor);
    Distribuidor updateDistribuidor(Long id,Distribuidor distribuidor);
    boolean delete(Long id) throws Exception;
}
