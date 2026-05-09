package com.itm.biblioteca.repository;

import com.itm.biblioteca.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EscritoRepositoryTest {

    @Autowired
    private EscritoRepository escritoRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private EditorialRepository editorialRepository;

    @Test
    void testGuardarEscrito() {

        Editorial ed = new Editorial();
        ed.setId("ED-99");
        ed.setNombre("Editorial Mundo");
        editorialRepository.save(ed);


        Libro libro = new Libro();
        libro.setIsbn("ISBN-999");
        libro.setTitulo("Cien años de soledad");
        libro.setEditorial(ed);
        libroRepository.save(libro);


        Autor autor = new Autor();
        autor.setIdAutor("AUT-01"); // Revisa si tu campo se llama idAutor o id
        autor.setNombre("Gabriel");
        autor.setApellido("Garcia Marquez");
        autorRepository.save(autor);


        Escrito escrito = new Escrito();
        escrito.setId("ES101");
        escrito.setFechaEscrito(new Date());
        escrito.setCiudad("Aracataca");
        escrito.setLibro(libro);
        escrito.setAutor(autor);


        Escrito guardado = escritoRepository.save(escrito);


        assertNotNull(guardado);
        assertEquals("ES101", guardado.getId());
        assertEquals("Aracataca", guardado.getCiudad());
        assertEquals("Cien años de soledad", guardado.getLibro().getTitulo());
        assertEquals("Gabriel", guardado.getAutor().getNombre());
    }
}