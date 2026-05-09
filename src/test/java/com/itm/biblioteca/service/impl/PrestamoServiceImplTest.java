package com.itm.biblioteca.service.impl;

import com.itm.biblioteca.model.*;
import com.itm.biblioteca.repository.PrestamoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrestamoServiceImplTest {

    @Mock
    private PrestamoRepository prestamoRepository;

    @InjectMocks
    private PrestamoServiceImpl prestamoService;

    @Test
    @DisplayName("Debe listar todos los préstamos llamando al repositorio")
    void listarTodos_RetornaListaDePrestamos() {
        // GIVEN
        Prestamo p = new Prestamo();
        when(prestamoRepository.findAll()).thenReturn(List.of(p));

        // WHEN
        List<Prestamo> resultado = prestamoService.listarTodos();

        // THEN
        assertThat(resultado).hasSize(1);
        verify(prestamoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe buscar un préstamo por su ID")
    void buscarPorId_LlamaAlRepositorioConIdCorrecto() {
        // GIVEN
        String id = "P100";
        when(prestamoRepository.findById(id)).thenReturn(Optional.empty());

        // WHEN
        prestamoService.buscarPorId(id);

        // THEN
        verify(prestamoRepository).findById(id);
    }

    @Test
    @DisplayName("Debe crear un préstamo correctamente con fechas válidas")
    void crear_RetornaPrestamoGuardado() {
        // GIVEN
        Prestamo prestamo = new Prestamo();
        prestamo.setIdPrestamo("PREST-01");
        prestamo.setMiembro(new Miembro());
        prestamo.setEjemplar(new Ejemplar());
        prestamo.setBibliotecario(new Bibliotecario());

        LocalDate hoy = LocalDate.now();
        prestamo.setFechaPrestamo(hoy);
        prestamo.setFechaDevolucion(hoy.plusDays(8));

        when(prestamoRepository.existsById("PREST-01")).thenReturn(false);
        when(prestamoRepository.save(any(Prestamo.class))).thenReturn(prestamo);

        // WHEN
        Prestamo resultado = prestamoService.crear(prestamo);

        // THEN
        assertThat(resultado).isNotNull();
        assertThat(resultado.getIdPrestamo()).isEqualTo("PREST-01");
        verify(prestamoRepository, times(1)).save(any(Prestamo.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción si la fecha de devolución es anterior a la de préstamo")
    void crear_CuandoFechaInvalida_DebeLanzarExcepcion() {
        // GIVEN
        Prestamo p = new Prestamo();
        p.setFechaPrestamo(LocalDate.now());
        p.setFechaDevolucion(LocalDate.now().minusDays(1)); // Ayer

        // WHEN & THEN
        RuntimeException ex = assertThrows(RuntimeException.class, () -> prestamoService.crear(p));

        assertThat(ex.getMessage()).isEqualTo("Error: Un préstamo debe tener un miembro, un ejemplar y un bibliotecario asignado.");
        verify(prestamoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe actualizar un préstamo existente")
    void actualizar_CuandoExiste_DebeRetornarPrestamoActualizado() {
        // GIVEN
        String id = "P001";
        Prestamo prestamoExistente = new Prestamo();
        prestamoExistente.setIdPrestamo(id);

        Prestamo datosNuevos = new Prestamo();
        datosNuevos.setFechaDevolucion(LocalDate.now().plusDays(15));
        datosNuevos.setMiembro(new Miembro());

        when(prestamoRepository.findById(id)).thenReturn(Optional.of(prestamoExistente));
        when(prestamoRepository.save(any(Prestamo.class))).thenAnswer(i -> i.getArguments()[0]);

        // WHEN
        Prestamo resultado = prestamoService.actualizar(id, datosNuevos);

        // THEN
        assertThat(resultado).isNotNull();
        verify(prestamoRepository).findById(id);
        verify(prestamoRepository).save(any(Prestamo.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción al intentar actualizar un préstamo inexistente")
    void actualizar_CuandoNoExiste_DebeLanzarExcepcion() {
        // GIVEN
        String idInexistente = "ID-999";
        when(prestamoRepository.findById(idInexistente)).thenReturn(Optional.empty());

        // WHEN & THEN
        RuntimeException exception = assertThrows(RuntimeException.class, () -> prestamoService.actualizar(idInexistente, new Prestamo()));

        assertThat(exception.getMessage()).isEqualTo("El prestamo con el ID " + idInexistente + " no existe");
        verify(prestamoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe eliminar préstamo si existe")
    void eliminar_LlamaAlRepositorioSiExiste() {
        // GIVEN
        String id = "ID-BORRAR";
        when(prestamoRepository.existsById(id)).thenReturn(true);

        // WHEN
        prestamoService.eliminar(id);

        // THEN
        verify(prestamoRepository).deleteById(id);
    }
}