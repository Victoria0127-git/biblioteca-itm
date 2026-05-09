package com.itm.biblioteca.util;

public class MiembroQueries {
    public static final String INSERT = "INSERT INTO Miembro (ID_Miembro, Nombre, Apellido, Telefono, Correo, Direccion) VALUES (?, ?, ?, ?, ?, ?)";
    public static final String GET_ALL = "SELECT ID_Miembro, Nombre, Apellido, Telefono, Correo, Direccion FROM Miembro";
    public static final String GET_BY_ID = "SELECT * FROM Miembro WHERE ID_Miembro = ?";
    public static final String UPDATE = "UPDATE Miembro SET Nombre=?, Apellido=?, Telefono=?, Correo=?, Direccion=? WHERE ID_Miembro=?";
    public static final String DELETE = "DELETE FROM Miembro WHERE ID_Miembro = ?";
    public static final String EXISTS_BY_ID = "SELECT COUNT(*) FROM Miembro WHERE ID_Miembro = ?";
}
