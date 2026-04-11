package com.itm.biblioteca.service.impl;

import com.itm.biblioteca.model.Libro;
import com.itm.biblioteca.repository.LibroRepository;
import com.itm.biblioteca.service.ILibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroServiceImpl implements ILibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Override
    public List<Libro> listarTodos() {
        return libroRepository.findAll();
    }

    @Override
    public Optional<Libro> buscarPorId(String isbn) {
        // Validamos que el ISBN no sea nulo antes de buscar
        if (isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException("El ISBN no puede estar vacío.");
        }
        return libroRepository.findById(isbn);
    }

    @Override
    public Libro guardar(Libro libro) {
        // Verificaciones de negocio para Libro
        if (libro.getIsbn() == null || libro.getIsbn().length() < 10) {
            throw new RuntimeException("El ISBN debe tener un formato válido.");
        }

        if (libro.getTitulo() == null || libro.getTitulo().isEmpty()) {
            throw new RuntimeException("El título del libro es obligatorio.");
        }

        // Si la editorial es obligatoria en tu modelo
        if (libro.getEditorial() == null) {
            throw new RuntimeException("Todo libro debe tener una editorial asignada.");
        }

        return libroRepository.save(libro);
    }

    @Override
    public void eliminar(String isbn) {
        if (!libroRepository.existsById(isbn)) {
            throw new RuntimeException("No se puede eliminar: El libro con ISBN " + isbn + " no existe.");
        }
        libroRepository.deleteById(isbn);
    }
}