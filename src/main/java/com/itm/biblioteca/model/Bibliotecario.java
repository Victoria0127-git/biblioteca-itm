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
}
