package com.backend.nova.Service.ContieneNMService;

import com.backend.nova.Entity.ContieneNM;
import com.backend.nova.Exception.CreateEntityException;
import com.backend.nova.Exception.DeleteEntityException;
import com.backend.nova.Exception.NotFoundEntityException;
import com.backend.nova.Exception.UpdateEntityException;
import com.backend.nova.Repository.IContieneNMRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContieneNMService implements IContieneNMService {

    @Autowired
    private IContieneNMRepository iContieneNMRepository;

    @Override
    public List<ContieneNM> findAll() {
        return (List<ContieneNM>) iContieneNMRepository.findAll();
    }

    @Override
    public ContieneNM findById(Long id) {
        return iContieneNMRepository.findById(id).orElseThrow(()->
                new NotFoundEntityException(id, ContieneNM.class.getSimpleName()));
    }

    @Override
    public ContieneNM crearContieneNM(ContieneNM contieneNM) {
        try {
            return iContieneNMRepository.save(contieneNM);
        } catch (Exception e) {
            throw new CreateEntityException(contieneNM, e);
        }
    }

    @Override
    public ContieneNM modificarContieneNM(Long id, ContieneNM contieneNM) {
        try {
            iContieneNMRepository.findById(id).orElseThrow(
                    () -> new Exception("ContieneNM no encontrado: " + id)
            );
            contieneNM.setId(id);
            return iContieneNMRepository.save(contieneNM);
        } catch (Exception e) {
            throw new UpdateEntityException("Error al modificar ContieneNM: " + id, e);
        }
    }

    @Override
    public boolean deleteById(Long id) throws Exception {
        try {
            iContieneNMRepository.findById(id).orElseThrow(
                    () -> new NotFoundEntityException(id, ContieneNM.class.getSimpleName())
            );
            iContieneNMRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new DeleteEntityException(id, ContieneNM.class.getSimpleName(), e);
        }
    }
}