package com.backend.nova.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "precio")
    private double precio;

    @Column(name = "entregado")
    private boolean entregado;

    @JsonIgnoreProperties("productos")
    @ManyToOne
    @JoinColumn(name = "id_contiene", referencedColumnName = "id")
    private ContieneNM contiene;

    @JsonIgnoreProperties("productos")
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "id_distribuidor")
    private Distribuidor distribuidor;
}