package com.backend.nova.Service.UsuarioService;

import com.backend.nova.Entity.Role;

import java.util.List;

public interface IRoleService
{
    List<Role> obtenerRolesByNombre(List<Integer> nombre);
}
