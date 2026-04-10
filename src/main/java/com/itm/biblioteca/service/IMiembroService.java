package com.itm.biblioteca.service;

import com.itm.biblioteca.model.Miembro;
import java.util.List;
import java.util.Optional;

public interface IMiembroService {
    List<Miembro> listarTodos();
    Optional<Miembro> buscarPorId(String id);
    Miembro guardar(Miembro miembro);
    void eliminar(String id);
}