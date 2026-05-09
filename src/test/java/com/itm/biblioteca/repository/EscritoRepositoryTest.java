package com.itm.biblioteca.repository;

import com.itm.biblioteca.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
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
    @DisplayName("Debe guardar un Escrito con sus relaciones de Libro, Autor y Editorial")
    void testGuardarEscrito() {
        // GIVEN: Preparar el árbol de dependencias

        // 1. Editorial
        Editorial ed = new Editorial();
        ed.setId("ED-99");
        ed.setNombre("Editorial Mundo");
        editorialRepository.save(ed);

        // 2. Libro (depende de Editorial)
        Libro libro = new Libro();
        libro.setIsbn("ISBN-999");
        libro.setTitulo("Cien años de soledad");
        libro.setEditorial(ed);
        libroRepository.save(libro);

        // 3. Autor
        Autor autor = new Autor();
        autor.setIdAutor("AUT-01");
        autor.setNombre("Gabriel");
        autor.setApellido("Garcia Marquez");
        autorRepository.save(autor);

        // 4. Escrito (depende de Libro y Autor)
        Escrito escrito = new Escrito();
        escrito.setId("ES101");
        escrito.setFechaEscrito(new Date());
        escrito.setCiudad("Aracataca");
        escrito.setLibro(libro);
        escrito.setAutor(autor);

        // WHEN: Guardar la entidad principal
        Escrito guardado = escritoRepository.save(escrito);

        // THEN: Verificaciones profundas con AssertJ
        Optional<Escrito> encontrado = escritoRepository.findById(guardado.getId());

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getCiudad()).isEqualTo("Aracataca");

        // Verificación de relaciones
        assertThat(encontrado.get().getLibro().getTitulo()).isEqualTo("Cien años de soledad");
        assertThat(encontrado.get().getLibro().getEditorial().getNombre()).isEqualTo("Editorial Mundo");
        assertThat(encontrado.get().getAutor().getNombre()).isEqualTo("Gabriel");
    }
}