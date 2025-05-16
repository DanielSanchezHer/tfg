package com.backend.nova.Entity;

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
@Table(name = "contiene")
public class ContieneNM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "cantidad")
    private int cantidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_carrito", nullable = false)
    private Carrito carrito;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;
}
