package com.itm.biblioteca.service.impl;

import com.itm.biblioteca.model.Editorial;
import com.itm.biblioteca.model.Libro;
import com.itm.biblioteca.repository.LibroRepository;
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
class LibroServiceImplTest {

    @Mock
    private LibroRepository libroRepository;

    @InjectMocks
    private LibroServiceImpl libroService;

    private Libro libro;

    @BeforeEach
    void setUp() {
        libro = new Libro();
        libro.setIsbn("1234567890");
        libro.setTitulo("Cien años de soledad");
        libro.setNumPag(400);

        Editorial editorialMock = new Editorial();
        libro.setEditorial(editorialMock);
    }

    @Test
    void listarTodos() {
        when(libroRepository.findAll()).thenReturn(Arrays.asList(libro));

        List<Libro> resultado = libroService.listarTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(libroRepository).findAll();
    }

    @Test
    void buscarPorId() {
        when(libroRepository.findById("1234567890")).thenReturn(Optional.of(libro));

        Optional<Libro> encontrado = libroService.buscarPorId("1234567890");

        assertTrue(encontrado.isPresent());
        assertEquals("Cien años de soledad", encontrado.get().getTitulo());
    }

    @Test
    void crear() {

        when(libroRepository.existsById("1234567890")).thenReturn(false);
        when(libroRepository.save(any(Libro.class))).thenReturn(libro);

        Libro creado = libroService.crear(libro);

        assertNotNull(creado);
        assertEquals("1234567890", creado.getIsbn());
        verify(libroRepository).save(any(Libro.class));
    }

    @Test
    void actualizar() {
        when(libroRepository.findById("1234567890")).thenReturn(Optional.of(libro));
        when(libroRepository.save(any(Libro.class))).thenReturn(libro);

        Libro resultado = libroService.actualizar("1234567890", libro);

        assertNotNull(resultado);
        assertEquals("Cien años de soledad", resultado.getTitulo());
        verify(libroRepository).save(any(Libro.class));
    }

    @Test
    void eliminar() {
        when(libroRepository.existsById("1234567890")).thenReturn(true);
        doNothing().when(libroRepository).deleteById("1234567890");

        libroService.eliminar("1234567890");

        verify(libroRepository).deleteById("1234567890");
    }
}