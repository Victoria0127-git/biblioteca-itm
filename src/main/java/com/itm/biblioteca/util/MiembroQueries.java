package com.itm.biblioteca.util;

public class MiembroQueries {
    public static final String INSERT = "INSERT INTO Miembros (nombre, apellido, direccion, telefono) VALUES (?, ?, ?, ?)";
    public static final String GET_ALL = "SELECT ID_Miembro, nombre, apellido, direccion, telefono FROM Miembros";
    public static final String GET_BY_ID = "SELECT * FROM Miembros WHERE ID_Miembro = ?";
    public static final String UPDATE = "UPDATE Miembros SET nombre=?, apellido=?, direccion=?, telefono=? WHERE ID_Miembro=?";
    public static final String DELETE = "DELETE FROM Miembros WHERE ID_Miembro = ?";
}
