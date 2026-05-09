package com.itm.biblioteca.repository;

import com.itm.biblioteca.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PrestamoRepositoryTest {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private MiembroRepository miembroRepository;

    @Autowired
    private EjemplarRepository ejemplarRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private EditorialRepository editorialRepository;



    @Test
    void testGuardarYBuscarPrestamo() {

        Miembro miembro = new Miembro();
        miembro.setIdMiembro("M-TEST");
        miembro.setNombre("Salome");
        miembroRepository.save(miembro);


        Editorial ed = new Editorial();
        ed.setId("ED-TEST");
        ed.setNombre("Editorial Test");
        editorialRepository.save(ed);

        Libro libro = new Libro();
        libro.setIsbn("ISBN-TEST");
        libro.setTitulo("Libro de Pruebas");
        libro.setEditorial(ed);
        libroRepository.save(libro);

        Ejemplar ej = new Ejemplar();
        ej.setId("EJ-TEST");
        ej.setEstado(true);
        ej.setLibro(libro);
        ejemplarRepository.save(ej);


        Prestamo prestamo = new Prestamo();
        prestamo.setIdPrestamo("P-001");
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setFechaDevolucion(LocalDate.now().plusDays(8));
        prestamo.setMiembro(miembro);
        prestamo.setEjemplar(ej);



        Prestamo guardado = prestamoRepository.save(prestamo);


        assertNotNull(guardado);
        assertEquals("P-001", guardado.getIdPrestamo());
        assertEquals("Salome", guardado.getMiembro().getNombre());
        assertEquals("EJ-TEST", guardado.getEjemplar().getId());

        System.out.println("Préstamo guardado con éxito para el miembro: " + guardado.getMiembro().getNombre());
    }
}