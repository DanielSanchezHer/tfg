package com.backend.nova.Repository;

import com.backend.nova.Entity.Usuario;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Hidden
@Repository
public interface IUsuarioRepository extends CrudRepository<Usuario, Long>
{
    Optional<Usuario> findByUsername(String username);

    boolean existsByUsername(String username);
}
