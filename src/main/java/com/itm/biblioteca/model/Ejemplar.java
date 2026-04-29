package com.itm.biblioteca.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
}
