package com.itm.biblioteca.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "Libro")
@Schema(description = "Representa un libro dentro del sistema biblioteca")
public class Libro {
    @Id
    @Column(name = "ISBN")
    @Schema(example = "9780135398524", description = "Identificado único de cada ejemplar de 13 digitos")
    private String isbn;

    @Column(name = "Titulo")
    @Schema(example = "Clean Code")
    private String titulo;

    @Column(name = "NumPag")
    @Schema(example = "462", description = "Número de paginas del libro")
    private int numPag;

    @ManyToOne //Llave foranea
    @JoinColumn(name = "ID_Editorial") Editorial editorial;
}