package com.itm.biblioteca.service.impl;

import com.itm.biblioteca.model.Editorial;
import com.itm.biblioteca.model.Libro;
import com.itm.biblioteca.repository.LibroRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LibroServiceImplTest {

    @Mock
    private LibroRepository libroRepository;

    @InjectMocks
    private LibroServiceImpl libroService;

    @Test
    @DisplayName("Debe listar todos los libros")
    void listarTodos_RetornaListaDeLibros() {
        // GIVEN
        Libro libro = new Libro();
        libro.setTitulo("Clean Code");
        when(libroRepository.findAll()).thenReturn(List.of(libro));

        // WHEN
        List<Libro> resultado = libroService.listarTodos();

        // THEN
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getTitulo()).isEqualTo("Clean Code");
        verify(libroRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe buscar un libro por su ISBN")
    void buscarPorId_RetornaLibroSiExiste() {
        // GIVEN
        String isbn = "1234567890";
        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo("Cien años de soledad");
        when(libroRepository.findById(isbn)).thenReturn(Optional.of(libro));

        // WHEN
        Optional<Libro> encontrado = libroService.buscarPorId(isbn);

        // THEN
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getTitulo()).isEqualTo("Cien años de soledad");
    }

    @Test
    @DisplayName("Debe crear un libro correctamente si tiene editorial")
    void crear_RetornaLibroGuardado() {
        // GIVEN
        Editorial editorial = new Editorial();
        editorial.setId("ED-1");

        Libro libro = new Libro();
        libro.setIsbn("1234567890");
        libro.setTitulo("Cien años de soledad");
        libro.setEditorial(editorial);

        when(libroRepository.existsById("1234567890")).thenReturn(false);
        when(libroRepository.save(any(Libro.class))).thenReturn(libro);

        // WHEN
        Libro creado = libroService.crear(libro);

        // THEN
        assertThat(creado).isNotNull();
        assertThat(creado.getIsbn()).isEqualTo("1234567890");
        verify(libroRepository, times(1)).save(any(Libro.class));
    }

    @Test
    @DisplayName("Debe fallar al crear si el libro no tiene editorial")
    void crear_FallaSiNoTieneEditorial() {
        // GIVEN
        Libro libroSinEditorial = new Libro();
        libroSinEditorial.setIsbn("000");
        libroSinEditorial.setEditorial(null);

        // WHEN & THEN
        RuntimeException ex = assertThrows(RuntimeException.class, () -> libroService.crear(libroSinEditorial));

        assertThat(ex.getMessage()).isEqualTo("El ISBN debe tener un formato válido.");
        verify(libroRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe actualizar el título y número de páginas de un libro")
    void actualizar_RetornaLibroActualizado() {
        // GIVEN
        String isbn = "1234567890";
        Libro datosNuevos = new Libro();
        datosNuevos.setTitulo("Cien años de soledad - Edición Especial");
        datosNuevos.setNumPag(450);

        Libro libroExistente = new Libro();
        libroExistente.setIsbn(isbn);
        libroExistente.setTitulo("Cien años de soledad - Edición Especial");
        libroExistente.setNumPag(400);

        when(libroRepository.findById(isbn)).thenReturn(Optional.of(libroExistente));
        when(libroRepository.save(any(Libro.class))).thenAnswer(i -> i.getArguments()[0]);

        // WHEN
        Libro resultado = libroService.actualizar(isbn, datosNuevos);

        // THEN
        assertThat(resultado.getTitulo()).isEqualTo("Cien años de soledad - Edición Especial");
        assertThat(resultado.getNumPag()).isEqualTo(450);
        verify(libroRepository).save(any(Libro.class));
    }

    @Test
    @DisplayName("Debe eliminar el libro si existe por ISBN")
    void eliminar_LlamaAlRepositorioSiExiste() {
        // GIVEN
        String isbn = "1234567890";
        when(libroRepository.existsById(isbn)).thenReturn(true);
        doNothing().when(libroRepository).deleteById(isbn);

        // WHEN
        libroService.eliminar(isbn);

        // THEN
        verify(libroRepository, times(1)).deleteById(isbn);
    }
}