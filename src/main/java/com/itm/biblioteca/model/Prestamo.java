package com.itm.biblioteca.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Setter
@Getter
@Entity
@Table (name = "Prestamo")
public class Prestamo {
    @Id
    @Column(name = "ID_Prestamo")
    private String idPrestamo;

    @Column(name = "Fecha_Prestamo")
    private Date fechaPrestamo;

    @Column(name = "Fecha_Devolucion")
    private Date fechaDevolucion;

    @ManyToOne
    @JoinColumn(name = "ID_Miembro")
    private Miembro miembro;

    @ManyToOne
    @JoinColumn(name = "ID_Ejemplar")
    private Ejemplar ejemplar;

    @ManyToOne
    @JoinColumn(name = "ID_Bibliotecario")
    private Bibliotecario bibliotecario;

    public Prestamo() {}

    public Prestamo(String idPrestamo,  Date fechaPrestamo, Date fechaDevolucion, Miembro miembro, Ejemplar ejemplar, Bibliotecario bibliotecario) {
        this.idPrestamo = idPrestamo;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.miembro = miembro;
        this.ejemplar = ejemplar;
        this.bibliotecario = bibliotecario;
    }
}
