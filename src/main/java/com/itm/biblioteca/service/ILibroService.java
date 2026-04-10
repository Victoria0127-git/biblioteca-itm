package com.itm.biblioteca.service;

import com.itm.biblioteca.model.Libro;
import java.util.List;
import java.util.Optional;

public interface ILibroService {
    List<Libro> listarTodos();
    Optional<Libro> buscarPorId(String id);
    Miembro guardar(Libro libro);
    void eliminar(String id);
}