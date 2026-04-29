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
@Table (name = "Editorial")
public class Editorial {
    @Id
    @Column(name = "ID_Editorial")
    private String id;

    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "Direccion")
    private String direccion;

    @Column(name = "Telefono")
    private String telefono;
}
