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
@Table (name = "Editorial")
@Schema(description = "Representa una editorial del sistema biblioteca")
public class Editorial {
    @Id
    @Column(name = "ID_Editorial")
    @Schema(example = "E001", description = "Identificador único alfanúmerico")
    private String id;

    @Column(name = "Nombre")
    @Schema(example = "albaricoque")
    private String nombre;

    @Column(name = "Direccion")
    @Schema(example = "Avenida Calle 100 No 49-97")
    private String direccion;

    @Column(name = "Telefono")
    @Schema(example = "3128062340")
    private String telefono;
}
