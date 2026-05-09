package com.itm.biblioteca.repository;

import com.itm.biblioteca.model.Editorial;
import com.itm.biblioteca.model.Libro;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class LibroRepositoryTest {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private EditorialRepository editorialRepository;

    @Test
    @DisplayName("Debe persistir un Libro vinculado a una Editorial y recuperarlo por ISBN")
    void testGuardarYBuscarLibro() {
        // GIVEN: Primero persistimos la Editorial (padre)
        Editorial ed = new Editorial();
        ed.setId("ED-PLANETA");
        ed.setNombre("Planeta");
        editorialRepository.save(ed);

        // Creamos el Libro vinculado a esa Editorial
        Libro libro = new Libro();
        libro.setIsbn("9780135398524");
        libro.setTitulo("Clean Code");
        libro.setNumPag(462);
        libro.setEditorial(ed);

        // WHEN: Guardamos el libro
        Libro guardado = libroRepository.save(libro);

        // THEN: Verificamos la persistencia y la integridad de la relación
        Optional<Libro> encontradoOpt = libroRepository.findById("9780135398524");

        assertThat(encontradoOpt).isPresent();
        Libro encontrado = encontradoOpt.get();

        assertThat(encontrado.getIsbn()).isEqualTo("9780135398524");
        assertThat(encontrado.getTitulo()).isEqualTo("Clean Code");
        assertThat(encontrado.getEditorial().getNombre()).isEqualTo("Planeta");
        assertThat(encontrado.getNumPag()).isEqualTo(462);
    }
}