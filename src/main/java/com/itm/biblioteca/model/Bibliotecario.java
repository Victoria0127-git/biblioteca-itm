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
@Table (name = "Bibliotecario")
@Schema(description = "Representan un bibliotecario dentro del sistema de biblioteca")
public class Bibliotecario {
    //Getters y Setters
    @Id
    @Column(name = "ID_Bibliotecario")
    @Schema(example = "B001", description = "Identificador único alfanúmerico")
    private String idBibliotecario;

    @Column (name = "Nombre")
    @Schema(example = "Rodrigo")
    private String nombre;

    @Column (name ="Apellido")
    @Schema(example = "Pelaez")
    private String apellido;
}
