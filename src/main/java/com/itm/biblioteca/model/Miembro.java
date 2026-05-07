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
@Table(name = "Miembro")
@Schema(description = "Representa un usuario o miembro dentro del sistema biblioteca")
public class Miembro {

    @Id
    @Column(name = "ID_Miembro") //Llave primaria
    @Schema(example = "M001", description = "Identificador único alfanúmerico")
    private String idMiembro;

    @Column(name = "Nombre")
    @Schema(example = "Paulina")
    private String nombre;

    @Column(name = "Apellido")
    @Schema(example = "Ochoa")
    private String apellido;

    @Column(name = "Correo")
    @Schema(example = "pauliOchoa@correo.com")
    private String correo;

    @Column(name = "Direccion")
    @Schema(example = "CLL32 A # 99CC - 214")
    private String direccion;

    @Column(name = "Telefono")
    @Schema(example = "3126541287")
    private String telefono;
}