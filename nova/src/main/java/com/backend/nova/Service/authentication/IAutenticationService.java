package com.backend.nova.Service.authentication;

import com.backend.nova.Entity.Usuario;

public interface IAutenticationService {
    public Usuario signup(Usuario newUser);

    public String authenticate(Usuario user);

}
