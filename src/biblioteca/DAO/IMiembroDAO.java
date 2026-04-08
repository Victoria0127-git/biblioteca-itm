package biblioteca.DAO;
import biblioteca.Model.Miembro;
import java.util.List;

public interface IMiembroDAO {
    //Definir operaciones CRUD
    void Crear(Miembro miembro);
    void Actualizar(Miembro miembro);
    void Eliminar(String id);
    Miembro ObtenerPorId(String id);
    List<Miembro> ObtenerTodos();
}
