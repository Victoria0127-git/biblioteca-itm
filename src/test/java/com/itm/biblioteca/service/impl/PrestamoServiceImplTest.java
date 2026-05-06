package com.itm.biblioteca.service.impl;

import com.itm.biblioteca.repository.PrestamoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrestamoServiceImplTest {

    @Mock
    private PrestamoRepository prestamoRepository;

    @InjectMocks
    private PrestamoServiceImpl prestamoService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void listarTodos() {
        prestamoService.listarTodos();
        verify(prestamoRepository, times(1)).findAll();
    }

    @Test
    void buscarPorId() {
        String id = "P100";
        when(prestamoRepository.findById(id)).thenReturn(java.util.Optional.empty());

        prestamoService.buscarPorId(id);

        verify(prestamoRepository).findById(id);
    }

    @Test
    void crear() {

        com.itm.biblioteca.model.Prestamo p = new com.itm.biblioteca.model.Prestamo();
        p.setIdPrestamo("PREST-01");
        p.setMiembro(new com.itm.biblioteca.model.Miembro());
        p.setEjemplar(new com.itm.biblioteca.model.Ejemplar());
        p.setBibliotecario(new com.itm.biblioteca.model.Bibliotecario());

        java.util.Date hoy = new java.util.Date();
        p.setFechaPrestamo(hoy);
        p.setFechaDevolucion(new java.util.Date(hoy.getTime() + 86400000));

        when(prestamoRepository.existsById("PREST-01")).thenReturn(false);
        when(prestamoRepository.save(any())).thenReturn(p);

        com.itm.biblioteca.model.Prestamo resultado = prestamoService.crear(p);

        assertNotNull(resultado);
        verify(prestamoRepository).save(any());
    }

    @Test
    void crear_CuandoFechaInvalida_DebeLanzarExcepcion() {

        com.itm.biblioteca.model.Prestamo p = new com.itm.biblioteca.model.Prestamo();
        p.setMiembro(new com.itm.biblioteca.model.Miembro());
        p.setEjemplar(new com.itm.biblioteca.model.Ejemplar());
        p.setBibliotecario(new com.itm.biblioteca.model.Bibliotecario());

        java.util.Date hoy = new java.util.Date();
        java.util.Date ayer = new java.util.Date(hoy.getTime() - 86400000);
        p.setFechaPrestamo(hoy);
        p.setFechaDevolucion(ayer);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> prestamoService.crear(p));
        assertEquals("La fecha de devolución no puede ser anterior a la fecha de préstamo.", ex.getMessage());
    }

    @Test
    void actualizar_CuandoExiste_DebeRetornarPrestamoActualizado() {

        String id = "P001";

        com.itm.biblioteca.model.Prestamo prestamoExistente = new com.itm.biblioteca.model.Prestamo();
        prestamoExistente.setIdPrestamo(id);

        com.itm.biblioteca.model.Prestamo datosNuevos = new com.itm.biblioteca.model.Prestamo();
        datosNuevos.setFechaPrestamo(new java.util.Date());
        datosNuevos.setFechaDevolucion(new java.util.Date());
        datosNuevos.setMiembro(new com.itm.biblioteca.model.Miembro());
        datosNuevos.setEjemplar(new com.itm.biblioteca.model.Ejemplar());
        datosNuevos.setBibliotecario(new com.itm.biblioteca.model.Bibliotecario());

        when(prestamoRepository.findById(id)).thenReturn(java.util.Optional.of(prestamoExistente));
        when(prestamoRepository.save(any(com.itm.biblioteca.model.Prestamo.class))).thenReturn(prestamoExistente);

        com.itm.biblioteca.model.Prestamo resultado = prestamoService.actualizar(id, datosNuevos);


        assertNotNull(resultado);
        verify(prestamoRepository).findById(id);
        verify(prestamoRepository).save(prestamoExistente);
    }
    @Test
    void actualizar_CuandoNoExiste_DebeLanzarExcepcion() {

        String idInexistente = "ID-999";
        com.itm.biblioteca.model.Prestamo datosNuevos = new com.itm.biblioteca.model.Prestamo();

        when(prestamoRepository.findById(idInexistente)).thenReturn(java.util.Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
       prestamoService.actualizar(idInexistente, datosNuevos);
        });

        assertEquals("El prestamo con el ID " + idInexistente + " no existe", exception.getMessage());
        verify(prestamoRepository, never()).save(any());
    }

    @Test
    void eliminar() {
        String id = "ID-BORRAR";
        when(prestamoRepository.existsById(id)).thenReturn(true);

        prestamoService.eliminar(id);

        verify(prestamoRepository).deleteById(id);
    }
}