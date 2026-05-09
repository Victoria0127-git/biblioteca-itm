package com.itm.biblioteca.repositorySQL;

import com.itm.biblioteca.model.Miembro;
import com.itm.biblioteca.util.MiembroQueries;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MiembroRepositorySQL {

    private static final Logger log = LoggerFactory.getLogger(MiembroRepositorySQL.class);

    private final DataSource dataSource;

    // List<T> listarTodos()
    public List<Miembro> getMiembros() {
        List<Miembro> result = new ArrayList<>();
        // El recurso se abre y se cierra automáticamente al final del bloque
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(MiembroQueries.GET_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                result.add(mapearMiembro(rs));
            }
        } catch (SQLException e) {
            log.error("Error al procesar la base de datos: {}", e.getMessage());
        }
        return result;
    }

    // Optional<T> buscarPorId(ID id)
    public Optional<Miembro> buscarPorId(String id) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(MiembroQueries.GET_BY_ID)) {

            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearMiembro(rs));
                }
            }
        } catch (SQLException e) {
            log.error("Error al buscar el miembro con ID: {}",id, e);
        }
        return Optional.empty();
    }

    // T crear(T entidad)
    public Miembro insertarMiembro(Miembro m) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(MiembroQueries.INSERT)) {

            ps.setString(1, m.getIdMiembro());
            ps.setString(2, m.getNombre());
            ps.setString(3, m.getApellido());
            ps.setString(4, m.getTelefono());
            ps.setString(5, m.getCorreo());
            ps.setString(6, m.getDireccion());
            ps.executeUpdate();
            return m;
        } catch (SQLException e) {
            log.error("Error al crear el miembro: {}", e.getMessage());
            return null;
        }
    }

    // T actualizar(ID id, T entidad)
    public Miembro actualizarMiembro(String id, Miembro m) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(MiembroQueries.UPDATE)) {

            ps.setString(1, m.getNombre());
            ps.setString(2, m.getApellido());
            ps.setString(3, m.getTelefono());
            ps.setString(4, m.getCorreo());
            ps.setString(5, m.getDireccion());
            ps.setString(6, id); // Parametro del WHERE
            ps.executeUpdate();
            return m;
        } catch (SQLException e) {
            log.error("Error al actualizar el miembro con ID: {}",id, e);
            return null;
        }
    }

    // void eliminar(ID id)
    public void eliminarMiembro(String id) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(MiembroQueries.DELETE)) {

            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Error al eliminar el miembro con ID: {}",id, e);
        }
    }

    public boolean existsById(String id) {
        // Usamos Try-with-resources para cerrar automaticamente
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(MiembroQueries.EXISTS_BY_ID)) {

            ps.setString(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Si el conteo es mayor a 0, el miembro existe
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            log.error("Error al verificar existencia del miembro con ID: {}", id, e);
        }
        return false;
    }

    /**
     * Mapea una fila del ResultSet a un objeto Miembro
     */
    private Miembro mapearMiembro(ResultSet rs) throws SQLException {
        Miembro m = new Miembro();
        m.setIdMiembro(rs.getString("ID_Miembro"));
        m.setNombre(rs.getString("Nombre"));
        m.setApellido(rs.getString("Apellido"));
        m.setDireccion(rs.getString("Direccion"));
        m.setCorreo(rs.getString("Correo"));
        m.setTelefono(rs.getString("Telefono"));
        return m;
    }
}