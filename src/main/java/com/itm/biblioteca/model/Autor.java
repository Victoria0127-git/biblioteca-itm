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
@Table(name = "Autor")
public class Autor {
    @Id
    @Column(name = "ID_Autor") //Verificar luego los nombres del DB
    private String idAutor;

    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "Apellido")
    private String apellido;

    @Column(name = "Nacionalidad")
    private String nacionalidad;

    public Autor() {}

    public Autor(String idAutor, String nombre, String apellido, String nacionalidad) {
        this.idAutor = idAutor;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nacionalidad = nacionalidad;
    }
}
