package com.itm.biblioteca.model;

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
public class Miembro {

    @Id
    @Column(name = "ID_Miembro") //Llave primaria
    private String idMiembro;

    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "Apellido")
    private String apellido;

    @Column(name = "Correo")
    private String correo;

    @Column(name = "Direccion")
    private String direccion;

    @Column(name = "Telefono")
    private String telefono;
}