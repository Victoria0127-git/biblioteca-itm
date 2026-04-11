package com.itm.biblioteca.service.impl;

import com.itm.biblioteca.model.Autor;
import com.itm.biblioteca.repository.AutorRepository;
import com.itm.biblioteca.service.IAutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutorServiceImpl implements IAutorService {

    @Autowired
    private AutorRepository autorRepository;

    @Override
    public List<Autor> listarTodos() {
        return autorRepository.findAll();
    }

    @Override
    public Optional<Autor> buscarPorId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El ID del autor no puede estar vacío.");
        }
        return autorRepository.findById(id);
    }

    @Override
    public Autor guardar(Autor autor) {
        // 1. Validar que el objeto no sea nulo
        if (autor == null) {
            throw new RuntimeException("No se puede guardar un autor nulo.");
        }

        // 2. Validar campos obligatorios
        if (autor.getIdAutor() == null || autor.getIdAutor().isBlank()) {
            throw new RuntimeException("El ID del autor es obligatorio.");
        }

        if (autor.getNombre() == null || autor.getNombre().isBlank()) {
            throw new RuntimeException("El nombre del autor es obligatorio.");
        }

        if (autor.getApellido() == null || autor.getApellido().isBlank()) {
            throw new RuntimeException("El apellido del autor es obligatorio.");
        }

        if (autor.getNacionalidad() == null || autor.getNacionalidad().isBlank()) {
            throw new RuntimeException("La nacionalildad del autor es obligatorio.");
        }

        return autorRepository.save(autor);
    }

    @Override
    public void eliminar(String id) {
        // Validar si existe antes de intentar borrar
        if (!autorRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: La editorial con ID " + id + " no existe.");
        }
        autorRepository.deleteById(id);
    }
}