package com.itm.biblioteca.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "Libro")
public class Libro {
    @Id
    @Column(name = "ISBN")
    private String isbn;

    @Column(name = "Titulo")
    private String titulo;

    @Column(name = "NumPag")
    private int numPag;

    @ManyToOne //Llave foranea
    @JoinColumn(name = "ID_Editorial") Editorial editorial;
}