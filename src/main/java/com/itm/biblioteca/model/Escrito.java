package com.itm.biblioteca.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.util.Date;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "Escrito")
@Schema(description = "Información del libro")
public class Escrito {
    @Id
    @Column(name = "ID_Escrito")
    @Schema(example = "ES101", description = "Identificador único alfanúmerico")
    private String id;

    @Column(name = "FechaEscirto")
    @Schema(example = "12-04-2026", description = "Fecha en la que se escribió el libro")
    private Date fechaEscrito;

    @Column(name = "Ciudad")
    @Schema(example = "Chile", description = "Ciudad en la que se escribió el libro")
    private String ciudad;

    @ManyToOne
    @JoinColumn (name = "ISBN")
    @Schema(example = "9780135398524")
    private Libro libro;

    @ManyToOne
    @JoinColumn (name = "ID_Autor")
    @Schema(example = "A000")
    private Autor autor;
}
