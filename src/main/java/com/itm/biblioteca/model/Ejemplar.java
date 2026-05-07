package com.itm.biblioteca.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "Ejemplar")
@Schema(description = "Representa un ejemplar dentro del sistema biblioteca")
public class Ejemplar {
    @Id
    @Column(name = "ID_Ejemplar")
    @Schema(example = "EJ001", description = "Identificador único alfanúmerico")
    private String id;

    @Column(name = "Ubicacion")
    @Schema(example = "CXJ910", description = "Ubicación en las estanterías de la biblioteca física")
    private String ubicacion;

    @Column(name = "Estado")
    @Schema(example = "True", description = "True significa que está disponible, false significa que fue prestado")
    private Boolean estado;

    @ManyToOne
    @JoinColumn(name = "ISBN")
    @Schema(example = "9780135398524", description = "Identificador único de cada ejemplar de 13 digitos")
    private Libro libro;
}
