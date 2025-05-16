package com.backend.nova.Service.UsuarioService;

import com.backend.nova.Entity.Role;
import com.backend.nova.Repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService implements IRoleService
{
    @Autowired
    IRoleRepository roleRepository;

    @Override
    public List<Role> obtenerRolesByNombre(List<Integer> nombre) {
        return roleRepository.findByNombreIn(nombre);
    }
}
