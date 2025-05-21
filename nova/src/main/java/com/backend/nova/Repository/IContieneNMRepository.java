package com.backend.nova.Repository;

import com.backend.nova.Entity.ContieneNM;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IContieneNMRepository extends CrudRepository<ContieneNM, Long> {
    List<ContieneNM> findByCarrito_Id(Long carritoId);
}
