package com.itm.biblioteca.model;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
}
