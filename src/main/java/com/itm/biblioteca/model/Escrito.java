package com.itm.biblioteca.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity
@Table (name = "Escrito")
public class Escrito {
    @Id
    @Column(name = "ID_Escrito")
    private String id;

    @Column(name = "FechaEscirto")
    private Date fechaEscrito;

    @Column(name = "Ciudad")
    private String ciudad;

    @ManyToOne
    @JoinColumn (name = "ISBN")
    private Libro libro;

    @ManyToOne
    @JoinColumn (name = "ID_Autor")
    private Autor autor;

    public Escrito() {}

    public Escrito(String id, String ciudad, Libro libro, Autor autor) {
        this.id = id;
        this.ciudad = ciudad;
        this.libro = libro;
        this.autor = autor;
    }

}
