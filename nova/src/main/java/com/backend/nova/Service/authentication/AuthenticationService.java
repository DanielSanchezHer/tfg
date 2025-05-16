package com.backend.nova.Service.authentication;


import com.backend.nova.Config.Security.JwtTokenProvider;
import com.backend.nova.Entity.Usuario;
import com.backend.nova.Repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthenticationService implements IAutenticationService
{

    @Autowired
    private IUsuarioRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public Usuario signup(Usuario newUser)
    {
        if(userRepository.existsByUsername(newUser.getUsername()))
        {
            throw new IllegalArgumentException("Username is already in use");
        }


        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setCreationDate(LocalDateTime.now());
        newUser.setUsername(newUser.getUsername());

        return userRepository.save(newUser);
    }

    @Override
    public String authenticate(Usuario user) {
        Authentication authResult = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authResult);

        return jwtTokenProvider.generateToken(authResult);
    }


}
