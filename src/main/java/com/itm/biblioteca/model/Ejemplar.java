package com.itm.biblioteca.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table (name = "Ejemplar")
public class Ejemplar {
    @Id
    @Column(name = "ID_Ejemplar")
    private String id;

    @Column(name = "Ubicacion")
    private String ubicacion;

    @Column(name = "Estado")
    private Boolean estado;

    @ManyToOne
    @JoinColumn(name = "ISBN")
    private Libro libro;

    public Ejemplar() {}

    public Ejemplar(String id, String ubicacion, Boolean estado, Libro libro) {
        this.id = id;
        this.ubicacion = ubicacion;
        this.estado = estado;
        this.libro = libro;
    }
}
