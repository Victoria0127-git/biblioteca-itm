package com.itm.biblioteca.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.time.LocalDate;
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
    @Schema(example = "E101", description = "Identificador único alfanúmerico")
    private String id;

    @Column(name = "FechaEscrito")
    @Schema(example = "12-04-2026", description = "Fecha en la que se escribió el libro")
    private LocalDate fechaEscrito = LocalDate.now();

    @Column(name = "Ciudad")
    @Schema(example = "Chile", description = "Ciudad en la que se escribió el libro")
    private String ciudad;

    @ManyToOne
    @JoinColumn (name = "ISBN")
    private Libro libro;

    @ManyToOne
    @JoinColumn (name = "ID_Autor")
    private Autor autor;
}
