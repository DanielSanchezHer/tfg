package com.backend.nova.Service.DistribuidorService;

import com.backend.nova.Entity.Distribuidor;
import com.backend.nova.Exception.CreateEntityException;
import com.backend.nova.Exception.DeleteEntityException;
import com.backend.nova.Exception.NotFoundEntityException;
import com.backend.nova.Exception.UpdateEntityException;
import com.backend.nova.Repository.IDistribuidorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistribuidorService implements IDistribuidorService{

    @Autowired
    private IDistribuidorRepository iDistribuidorRepository;

    @Override
    public List<Distribuidor> findAll() {
        return (List<Distribuidor>) iDistribuidorRepository.findAll();
    }

    @Override
    public Distribuidor findById(Long id) {
        return iDistribuidorRepository.findById(id).orElseThrow(()
                ->new NotFoundEntityException(id, Distribuidor.class.getSimpleName()));
    }

    @Override
    public Distribuidor crearDistribuidor(Distribuidor distribuidor) {
        try{
            return iDistribuidorRepository.save(distribuidor);
        }catch (Exception e){
            throw new CreateEntityException(distribuidor, e);
        }
    }

    @Override
    public Distribuidor updateDistribuidor(Long id, Distribuidor distribuidor) {
        Distribuidor distribuidor1 = iDistribuidorRepository.findById(id).orElseThrow(
                () -> new NotFoundEntityException(id, Distribuidor.class.getSimpleName())
        );
        distribuidor1.setNombre(distribuidor.getNombre());
        distribuidor1.setNif(distribuidor.getNif());
        try{
            return iDistribuidorRepository.save(distribuidor1);
        }catch (Exception e){
          throw new UpdateEntityException("Error al modificar el distribuidor con id: "+id, e);
        }

    }

    @Override
    public boolean delete(Long id) throws Exception {
        try{
            iDistribuidorRepository.findById(id).orElseThrow(
                    () -> new NotFoundEntityException(id, Distribuidor.class.getSimpleName())
            );
            iDistribuidorRepository.deleteById(id);
            return true;
        }catch (Exception e){
            throw new DeleteEntityException(id, Distribuidor.class.getSimpleName(), e);
        }
    }
}
