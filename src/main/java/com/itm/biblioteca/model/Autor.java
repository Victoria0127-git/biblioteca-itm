package com.itm.biblioteca.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.*;

@Data               // Genera Getters, Setters, toString, equals y hashCode
@Builder            // Permite el uso de Autor.builder()...
@NoArgsConstructor  // Genera el constructor vacío (Obligatorio para JPA)
@AllArgsConstructor // Genera el constructor con todos los campos (Obligatorio para Builder)
@Entity
@Table(name = "Autor")
public class Autor {

    @Id
    @Column(name = "ID_Autor")
    private String idAutor;

    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "Apellido")
    private String apellido;

    @Column(name = "Nacionalidad")
    private String nacionalidad;
}