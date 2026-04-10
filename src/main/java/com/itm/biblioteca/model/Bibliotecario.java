package com.itm.biblioteca.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table (name = "Bibliotecario")
public class Bibliotecario {
    //Getters y Setters
    @Id
    @Column(name = "ID_Bibliotecario")
    private String idBibliotecario;

    @Column (name = "Nombre")
    private String nombre;

    @Column (name ="Apellido")
    private String apellido;

    //Constructor vacío
    public Bibliotecario() {}

    //Constructor con parámetros
    public Bibliotecario(String idBibliotecario, String nombre, String apellido) {
        this.idBibliotecario = idBibliotecario;
        this.nombre = nombre;
        this.apellido = apellido;
    }

}
