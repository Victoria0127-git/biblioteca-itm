package com.itm.biblioteca.service.impl;

import com.itm.biblioteca.model.Ejemplar;
import com.itm.biblioteca.model.Libro;
import com.itm.biblioteca.repository.EjemplarRepository;
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
class EjemplarServiceImplTest {

    @Mock
    private EjemplarRepository ejemplarRepository;

    @InjectMocks
    private EjemplarServiceImpl ejemplarService;

    private Ejemplar ejemplar;
    private Libro libro;

    @BeforeEach
    void setUp() {

        libro = new Libro();
        libro.setIsbn("1234567890");
        libro.setTitulo("Cien años de soledad");

        ejemplar = new Ejemplar();
        ejemplar.setId("EJ-001");
        ejemplar.setUbicacion("Estante A-1");
        ejemplar.setEstado(true);
        ejemplar.setLibro(libro);
    }

    @Test
    void listarTodos() {
        when(ejemplarRepository.findAll()).thenReturn(Arrays.asList(ejemplar));

        List<Ejemplar> resultado = ejemplarService.listarTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(ejemplarRepository).findAll();
    }

    @Test
    void buscarPorId() {
        when(ejemplarRepository.findById("EJ-001")).thenReturn(Optional.of(ejemplar));

        Optional<Ejemplar> encontrado = ejemplarService.buscarPorId("EJ-001");

        assertTrue(encontrado.isPresent());
        assertEquals("Estante A-1", encontrado.get().getUbicacion());
    }

    @Test
    void crear() {

        when(ejemplarRepository.existsById("EJ-001")).thenReturn(false);
        when(ejemplarRepository.save(any(Ejemplar.class))).thenReturn(ejemplar);

        Ejemplar creado = ejemplarService.crear(ejemplar);

        assertNotNull(creado);
        assertEquals("EJ-001", creado.getId());

        assertNotNull(creado.getLibro());
        verify(ejemplarRepository).save(any(Ejemplar.class));
    }

    @Test
    void crearFallaSinLibro() {

        ejemplar.setLibro(null);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            ejemplarService.crear(ejemplar);
        });

        assertEquals("Un ejemplar debe estar asociado a un Libro (ISBN).", exception.getMessage());
    }

    @Test
    void actualizar() {
        when(ejemplarRepository.findById("EJ-001")).thenReturn(Optional.of(ejemplar));
        when(ejemplarRepository.save(any(Ejemplar.class))).thenReturn(ejemplar);

        Ejemplar resultado = ejemplarService.actualizar("EJ-001", ejemplar);

        assertNotNull(resultado);
        verify(ejemplarRepository).save(any(Ejemplar.class));
    }

    @Test
    void eliminar() {
        when(ejemplarRepository.existsById("EJ-001")).thenReturn(true);
        doNothing().when(ejemplarRepository).deleteById("EJ-001");

        ejemplarService.eliminar("EJ-001");

        verify(ejemplarRepository).deleteById("EJ-001");
    }
}