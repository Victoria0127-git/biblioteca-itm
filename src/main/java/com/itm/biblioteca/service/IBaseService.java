package com.itm.biblioteca.service;

import java.util.List;
import java.util.Optional;

public interface IBaseService<T, ID> {
    List<T> listarTodos();
    Optional<T> buscarPorId(ID id);
    T guardar(T entidad);
    void eliminar(ID id);
}
