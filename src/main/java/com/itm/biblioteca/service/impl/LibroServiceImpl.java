package com.itm.biblioteca.service.impl;

import com.itm.biblioteca.model.Libro;
import com.itm.biblioteca.repository.LibroRepository;
import com.itm.biblioteca.repository.PrestamoRepository;
import com.itm.biblioteca.service.ILibroService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LibroServiceImpl implements ILibroService {
    private final LibroRepository libroRepository;
    private final PrestamoRepository prestamoRepository;

    @Override
    public List<Libro> listarTodos() {
        return libroRepository.findByEstadoTrue();
    }

    @Override
    public Optional<Libro> buscarPorId(String isbn) {

        if (isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException("El ISBN no puede estar vacío.");
        }
        return libroRepository.findById(isbn);
    }

    @Override
    public Libro crear(Libro libro) {

        if (libro.getIsbn() == null || libro.getIsbn().length() < 10) {
            throw new RuntimeException("El ISBN debe tener un formato válido.");
        }


        if (libroRepository.existsById(libro.getIsbn())){
            throw new RuntimeException("Ya existe un libro registrado con el ISBN: "+libro.getIsbn());
        }

        if (libro.getTitulo() == null || libro.getTitulo().isEmpty()) {
            throw new RuntimeException("El título del libro es obligatorio.");
        }


        if (libro.getEditorial() == null) {
            throw new RuntimeException("Todo libro debe tener una editorial asignada.");
        }

        return libroRepository.save(libro);
    }

    @Override
    public Libro actualizar(String isbn, Libro libroActualizado) {
        return libroRepository.findById(isbn).
                map(libroExistente ->
                {
                    if ((libroActualizado.getIsbn() != null)) {
                        libroExistente.setTitulo(libroActualizado.getTitulo());
                    }
                    if ((libroActualizado.getNumPag() != 0)) {
                        libroExistente.setNumPag(libroActualizado.getNumPag());
                    }
                    if ((libroActualizado.getEditorial() != null)) {
                        libroExistente.setEditorial(libroActualizado.getEditorial());
                    }

                    return libroRepository.save(libroExistente);
                })
                .orElseThrow(() -> new RuntimeException("El libro con ISBN: "+isbn+" no existe"));
    }

    @Override
    public void eliminar(String isbn) {
        Libro libro = libroRepository.findById(isbn)
                .orElseThrow(() -> new RuntimeException("No existe el libro con isbn "+ isbn));

        //Verificar que no esté prestado
        if (prestamoRepository.existsByEjemplar_Libro_IsbnAndFechaDevolucionIsNull(libro.getIsbn())) {
            throw new RuntimeException("El libro no se puede eliminar porque tiene prestamos activos");
        }

        libro.setEstado(false); // Cambia el BIT a 0 en SQL Server
        libroRepository.save(libro);
    }
}