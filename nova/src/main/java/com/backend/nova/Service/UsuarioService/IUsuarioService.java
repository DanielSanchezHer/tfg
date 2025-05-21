package com.backend.nova.Service.UsuarioService;

import com.backend.nova.Entity.Carrito;
import com.backend.nova.Entity.Usuario;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUsuarioService extends UserDetailsService {
    Usuario findByUser(String username);
}
