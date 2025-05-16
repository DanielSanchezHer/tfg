package com.backend.nova.Entity;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuario")
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario implements UserDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //@Schema(description = "Nombre de usuario",nullable = false)
    @Column(unique = true, nullable = false)
    private String username;

    //@Schema(description = "Contraseña del usuario",nullable = false)
    @Column(nullable = false)
    private String password;

    //@Schema(description = "Dni del usuario",nullable = false)
    @Column(nullable = false)
    private String dni;

    //@Schema(description = "Fecha de ultimo login")
    @Column
    private LocalDateTime fecha_ultimo_login;

    //@Schema(description = "Estado del usuario")
    @Column
    private boolean activo;

    //@Schema(description = "Fecha de creacion del usuario",nullable = false)
    @Column(nullable = false)
    private LocalDateTime creationDate;

    //@Schema(description = "Email del usuario",nullable = false)
    @Column(nullable = false, unique = true)
    private String email;

    //@Schema(description = "Lista de roles del usuario")
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_codigo"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    //@Schema(description = "Lista de compras del usuario")
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(ur -> new SimpleGrantedAuthority("ROLE_" + ur.getNombre().toString())).collect(Collectors.toList());
    }
}
