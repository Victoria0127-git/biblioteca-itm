package biblioteca.DAO.impl;

import biblioteca.Model.Miembro;
import biblioteca.DAO.IMiembroDAO;
import biblioteca.Util.MiembroQueries;
//import biblioteca.Config.DataBaseConection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MiembroDAOImpl implements IMiembroDAO {
    @Override
    public void Crear(Miembro miembro) {
        // Llamamos a la constante del otro archivo
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(MiembroQueries.INSERT)) {

            pstmt.setString(1, miembro.getNombre());
            pstmt.setString(2, miembro.getApellido());
            pstmt.setString(3, miembro.getDireccion());
            pstmt.setString(4, miembro.getTelefono());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Actualizar(Miembro miembro) {
        // Usamos el mismo bloque try-with-resources para manejo manual de conexión
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(MiembroQueries.UPDATE)) {

            // Seteamos los valores de los campos que queremos cambiar
            pstmt.setString(1, miembro.getNombre());
            pstmt.setString(2, miembro.getApellido());
            pstmt.setString(3, miembro.getDireccion());
            pstmt.setString(4, miembro.getTelefono());

            // Seteamos el ID al final para el WHERE (Es el quinto signo de interrogación)
            pstmt.setString(5, miembro.getID_Miembro());

            // Ejecutamos la actualización
            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Miembro actualizado con éxito en SQL Server.");
            } else {
                System.out.println("No se encontró el miembro con ID: " + miembro.getID_Miembro());
            }

        } catch (SQLException e) {
            System.err.println("Error al actualizar miembro: " + e.getMessage());
        }
    }

    @Override
    public void Eliminar(String id) {
        // Manejo manual de conexión
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(MiembroQueries.DELETE)) {

            // Solo tenemos un signo de interrogación (el ID)
            pstmt.setString(1, id);

            // Ejecutamos la eliminación
            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Miembro con ID " + id + " eliminado correctamente.");
            } else {
                System.out.println("No se encontró ningún miembro con el ID: " + id);
            }

        } catch (SQLException e) {
            System.err.println("Error al intentar eliminar: " + e.getMessage());
        }
    }

    @Override
    public List<Miembro> ObtenerTodos() {
        List<Miembro> lista = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(MiembroQueries.GET_ALL);
             ResultSet rs = pstmt.executeQuery()) { // executeQuery() para SELECTs

            // El ResultSet es como un cursor, lo recorremos fila por fila
            while (rs.next()) {
                Miembro m = new Miembro();
                // Llenamos el objeto con los datos de la columna de la DB
                m.setID_Miembro(rs.getString("id_miembro"));
                m.setNombre(rs.getString("nombre"));
                m.setApellido(rs.getString("apellido"));
                m.setDireccion(rs.getString("direccion"));
                m.setTelefono(rs.getString("telefono"));

                lista.add(m); // Agregamos a la lista
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public Miembro ObtenerPorId(String id) {
        Miembro miembro = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(MiembroQueries.GET_BY_ID)) {

            pstmt.setString(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) { // Solo esperamos un resultado (o ninguno)
                    miembro = new Miembro();
                    miembro.setID_Miembro(rs.getString("id_miembro"));
                    miembro.setNombre(rs.getString("nombre"));
                    miembro.setApellido(rs.getString("apellido"));
                    miembro.setDireccion(rs.getString("direccion"));
                    miembro.setTelefono(rs.getString("telefono"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return miembro; // Si no lo encuentra, devuelve null
    }
}
