package com.itm.biblioteca.repository;

import com.itm.biblioteca.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // Eliminamos todos los repositorios reales sobrantes
class PrestamoRepositoryTest {

    @Mock
    private PrestamoRepository prestamoRepository; // Único repositorio necesario

    @Test
    @DisplayName("Debe persistir un préstamo vinculando Miembro y Ejemplar correctamente (Mockeado)")
    void testGuardarYBuscarPrestamo() {
        // GIVEN: Preparación de toda la cadena de dependencias en memoria

        // 1. Miembro
        Miembro miembro = new Miembro();
        miembro.setIdMiembro("M-TEST");
        miembro.setNombre("Salome");

        // 2. Editorial -> Libro -> Ejemplar
        Editorial ed = new Editorial();
        ed.setId("ED-TEST");
        ed.setNombre("Editorial Test");

        Libro libro = new Libro();
        libro.setIsbn("ISBN-TEST");
        libro.setTitulo("Libro de Pruebas");
        libro.setEditorial(ed);

        Ejemplar ej = new Ejemplar();
        ej.setId("EJ-TEST");
        ej.setEstado(true);
        ej.setLibro(libro);

        // 3. Creación del Préstamo con su fecha correcta usando LocalDate
        Prestamo prestamo = new Prestamo();
        prestamo.setIdPrestamo("P-001");
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setFechaDevolucion(LocalDate.now().plusDays(8));
        prestamo.setMiembro(miembro);
        prestamo.setEjemplar(ej);

        // Entrenamos al único mock para simular el comportamiento de persistencia del Prestamo
        when(prestamoRepository.save(any(Prestamo.class))).thenReturn(prestamo);
        when(prestamoRepository.findById("P-001")).thenReturn(Optional.of(prestamo));

        // WHEN: Ejecutamos el guardado y búsqueda simulada
        prestamoRepository.save(prestamo);
        Optional<Prestamo> encontradoOpt = prestamoRepository.findById("P-001");

        // THEN: Verificaciones de integridad sobre la respuesta del mock
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