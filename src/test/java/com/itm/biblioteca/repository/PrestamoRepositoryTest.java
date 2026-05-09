package com.itm.biblioteca.repository;

import com.itm.biblioteca.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
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
    @DisplayName("Debe persistir un préstamo vinculando Miembro y Ejemplar correctamente")
    void testGuardarYBuscarPrestamo() {
        // GIVEN: Preparación de toda la cadena de dependencias

        // 1. Miembro
        Miembro miembro = new Miembro();
        miembro.setIdMiembro("M-TEST");
        miembro.setNombre("Salome");
        miembroRepository.save(miembro);

        // 2. Editorial -> Libro -> Ejemplar
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

        // 3. Creación del Préstamo
        Prestamo prestamo = new Prestamo();
        prestamo.setIdPrestamo("P-001");
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setFechaDevolucion(LocalDate.now().plusDays(8));
        prestamo.setMiembro(miembro);
        prestamo.setEjemplar(ej);

        // WHEN: Guardar el préstamo
        Prestamo guardado = prestamoRepository.save(prestamo);

        // THEN: Verificaciones de integridad
        Optional<Prestamo> encontradoOpt = prestamoRepository.findById(guardado.getIdPrestamo());

        assertThat(encontradoOpt).isPresent();
        Prestamo encontrado = encontradoOpt.get();

        assertThat(encontrado.getIdPrestamo()).isEqualTo("P-001");
        assertThat(encontrado.getMiembro().getNombre()).isEqualTo("Salome");
        assertThat(encontrado.getEjemplar().getId()).isEqualTo("EJ-TEST");
        assertThat(encontrado.getFechaDevolucion()).isEqualTo(LocalDate.now().plusDays(8));

        // Verificación de la relación profunda (transitiva)
        assertThat(encontrado.getEjemplar().getLibro().getTitulo()).isEqualTo("Libro de Pruebas");
    }
}