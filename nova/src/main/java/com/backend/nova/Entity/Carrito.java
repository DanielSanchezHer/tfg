package com.backend.nova.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "carrito")
public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "fecha_compra")
    private LocalDateTime fechaCompra;

    @Column(name = "finalizado")
    private boolean finalizado;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    @JsonBackReference
    private Cliente cliente;

    @JsonIgnore
    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ContieneNM> contiene;
}