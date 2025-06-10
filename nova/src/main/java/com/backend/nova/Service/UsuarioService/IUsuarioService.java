package com.backend.nova.Service.UsuarioService;

import com.backend.nova.Entity.Carrito;
import com.backend.nova.Entity.Usuario;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface IUsuarioService extends UserDetailsService {
    Usuario findByUser(String username);
    Usuario findById(Long id);
}
