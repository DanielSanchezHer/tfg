package com.backend.nova.Repository;

import com.backend.nova.Entity.ContieneNM;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Hidden
@Repository
public interface IContieneNMRepository extends CrudRepository<ContieneNM, Long> {
    List<ContieneNM> findByCarrito_Id(Long carritoId);
}
