package com.itm.biblioteca.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Autor")
@Schema(description = "Representa un autor dentro del sistema de biblioteca") // Describe la entidad global
public class Autor {

    @Id
    @Column(name = "ID_Autor")
    @Schema(example = "A001", description = "Identificador único alfanumérico")
    private String idAutor;

    @Column(name = "Nombre")
    @Schema(example = "Gabriel", description = "Primer y segundo nombre del autor")
    private String nombre;

    @Column(name = "Apellido")
    @Schema(example = "García Márquez", description = "Apellidos completos")
    private String apellido;

    @Column(name = "Nacionalidad")
    @Schema(example = "Colombiano", description = "País de origen del autor")
    private String nacionalidad;
}