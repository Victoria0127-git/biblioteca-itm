package com.itm.biblioteca.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

    @Column(name = "Direccion")
    private String direccion;

    @Column(name = "Telefono")
    private String telefono;

    // 1. Constructor vacío (Obligatorio para JPA)
    public Miembro() {}

    // 2. Constructor con parámetros
    public Miembro(String idMiembro, String nombre, String apellido, String direccion, String telefono) {
        this.idMiembro = idMiembro;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.telefono = telefono;
    }
}