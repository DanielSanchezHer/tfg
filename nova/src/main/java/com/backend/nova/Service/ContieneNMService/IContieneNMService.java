package com.backend.nova.Service.ContieneNMService;

import com.backend.nova.Entity.ContieneNM;

import java.util.List;

public interface IContieneNMService {
    List<ContieneNM> findAll();
    ContieneNM findById(Long id);
    ContieneNM crearContieneNM(ContieneNM contieneNM);
    ContieneNM modificarContieneNM(Long id,ContieneNM contieneNM);
    boolean deleteById(Long id) throws Exception;
}
