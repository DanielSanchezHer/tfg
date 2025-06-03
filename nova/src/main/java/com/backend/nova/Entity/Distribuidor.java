package com.backend.nova.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "distribuidor")
public class Distribuidor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "nif")
    private String nif;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "email")
    private String email;

    @Column(name = "nombre_repartidor")
    private String nombreRepartidor;

    @OneToOne
    @JoinColumn(name = "id_proveedor")
    //@JsonBackReference
    private Proveedor proveedor;

    //@JsonManagedReference
    @OneToMany(mappedBy = "distribuidor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Producto> productos;
}