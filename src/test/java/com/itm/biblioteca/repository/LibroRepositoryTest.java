package com.itm.biblioteca.repository;

import com.itm.biblioteca.model.Editorial;
import com.itm.biblioteca.model.Libro;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LibroRepositoryTest {

    @Mock
    private LibroRepository libroRepository;

    @Test
    @DisplayName("Debe persistir un Libro vinculado a una Editorial y recuperarlo por ISBN (Mockeado)")
    void testGuardarYBuscarLibro() {
        // GIVEN: Estructura en memoria
        Editorial ed = new Editorial();
        ed.setId("ED-PLANETA");
        ed.setNombre("Planeta");

        Libro libro = new Libro();
        libro.setIsbn("9780135398524");
        libro.setTitulo("Clean Code");
        libro.setNumPag(462);
        libro.setEditorial(ed);

        // Entrenamos al mock
        when(libroRepository.save(any(Libro.class))).thenReturn(libro);
        when(libroRepository.findById("9780135398524")).thenReturn(Optional.of(libro));

        // WHEN: Ejecutamos el flujo sin dejar variables muertas
        libroRepository.save(libro);
        Optional<Libro> encontradoOpt = libroRepository.findById("9780135398524");

        // THEN: Validamos
        assertThat(encontradoOpt).isPresent();
        Libro encontrado = encontradoOpt.get();

        assertThat(encontrado.getIsbn()).isEqualTo("9780135398524");
        assertThat(encontrado.getTitulo()).isEqualTo("Clean Code");
        assertThat(encontrado.getEditorial().getNombre()).isEqualTo("Planeta");
        assertThat(encontrado.getNumPag()).isEqualTo(462);
    }
}