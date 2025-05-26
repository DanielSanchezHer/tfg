package com.backend.nova.Repository;

import com.backend.nova.Entity.Distribuidor;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Hidden
@Repository
public interface IDistribuidorRepository extends CrudRepository<Distribuidor, Long> {
}
