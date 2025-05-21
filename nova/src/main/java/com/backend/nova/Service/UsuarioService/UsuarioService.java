package com.backend.nova.Service.UsuarioService;


import com.backend.nova.Entity.Carrito;
import com.backend.nova.Entity.Usuario;
import com.backend.nova.Exception.NotFoundEntityException;
import com.backend.nova.Repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements IUsuarioService
{
    @Autowired
    private IUsuarioRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities()
        );
    }
    @Override
    public Usuario findByUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(()
                -> new NotFoundEntityException(username, Usuario.class.getSimpleName()));
    }
}
