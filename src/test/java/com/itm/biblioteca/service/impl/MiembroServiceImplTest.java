package com.itm.biblioteca.service.impl;

import com.itm.biblioteca.model.Miembro;
import com.itm.biblioteca.repository.MiembroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MiembroServiceImplTest {

    @Mock
    private MiembroRepository miembroRepository;

    @InjectMocks
    private MiembroServiceImpl miembroService;

    private Miembro miembro;

    @BeforeEach
    void setUp() {
        miembro = new Miembro();
        miembro.setIdMiembro("123");
        miembro.setNombre("Victoria");
        miembro.setCorreo("victoria@itm.edu.co");
    }

    @Test
    void listarTodos() {
        when(miembroRepository.findAll()).thenReturn(Arrays.asList(miembro));
        List<Miembro> resultado = miembroService.listarTodos();
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    void crear() {

        when(miembroRepository.existsById("123")).thenReturn(false);
        when(miembroRepository.save(any(Miembro.class))).thenReturn(miembro);

        Miembro creado = miembroService.crear(miembro);

        assertNotNull(creado);
        assertEquals("Victoria", creado.getNombre());
        verify(miembroRepository).save(miembro);
    }

    @Test
    void buscarPorId() {
        when(miembroRepository.findById("123")).thenReturn(Optional.of(miembro));
        Optional<Miembro> encontrado = miembroService.buscarPorId("123");
        assertTrue(encontrado.isPresent());
        assertEquals("123", encontrado.get().getIdMiembro());
    }

    @Test
    void actualizar() {
        when(miembroRepository.findById("123")).thenReturn(Optional.of(miembro));
        when(miembroRepository.save(any(Miembro.class))).thenReturn(miembro);

        Miembro resultado = miembroService.actualizar("123", miembro);

        assertNotNull(resultado);
        verify(miembroRepository).save(any(Miembro.class));
    }

    @Test
    void eliminar() {

        when(miembroRepository.existsById("123")).thenReturn(true);
        doNothing().when(miembroRepository).deleteById("123");

        miembroService.eliminar("123");

        verify(miembroRepository).deleteById("123");
    }
}