package com.itm.biblioteca.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "Miembro") // Asegúrate de que este sea el nombre exacto de tu tabla en SQL
public class Miembro {

    @Id
    @Column(name = "ID_Miembro") // Define que esta es tu llave primaria
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

    // 3. Getters y Setters (Estilo CamelCase)
    public String getIdMiembro() { return idMiembro; }
    public void setIdMiembro(String idMiembro) { this.idMiembro = idMiembro; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}