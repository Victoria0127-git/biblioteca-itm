package com.itm.biblioteca.model;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
}
