package com.itm.biblioteca.repository;

import com.itm.biblioteca.model.Editorial;
import com.itm.biblioteca.model.Libro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LibroRepositoryTest {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private EditorialRepository editorialRepository;

    @Test
    void testGuardarYBuscarLibro() {

        Editorial ed = new Editorial();
        ed.setId("ED-PLANETA");
        ed.setNombre("Planeta");
        editorialRepository.save(ed);


        Libro libro = new Libro();
        libro.setIsbn("9780135398524");
        libro.setTitulo("Clean Code");
        libro.setNumPag(462);
        libro.setEditorial(ed);


        Libro guardado = libroRepository.save(libro);


        assertNotNull(guardado);
        assertEquals("9780135398524", guardado.getIsbn());
        assertEquals("Planeta", guardado.getEditorial().getNombre());


        Libro encontrado = libroRepository.findById("9780135398524").orElse(null);
        assertNotNull(encontrado);
        assertEquals("Clean Code", encontrado.getTitulo());
    }
}