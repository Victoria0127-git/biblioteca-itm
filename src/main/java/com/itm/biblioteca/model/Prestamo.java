package com.itm.biblioteca.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "Prestamo")
@Schema(description = "Representa un prestamo de un ejemplar dentro del sistema")
public class Prestamo {
    @Id
    @Column(name = "ID_Prestamo")
    @Schema(example = "P00123", description = "Identificador único alfanúmerico")
    private String idPrestamo;

    @Column(name = "Fecha_Prestamo")
    @Schema(example = "26-04-13", description = "Fecha en la que se realizó el prestamo")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaPrestamo;

    @Column(name = "Fecha_Devolucion")
    @Schema(example = "2026-05-08", description = "Fecha en la que se devolvió el prestamo")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaDevolucion;

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
