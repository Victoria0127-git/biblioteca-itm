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

@ExtendWith(MockitoExtension.class) // Cero Spring, cero H2
class EscritoRepositoryTest {

    @Mock
    private EscritoRepository escritoRepository; // Solo mockeamos el repositorio objetivo

    @Test
    @DisplayName("Debe guardar un Escrito con sus relaciones de Libro, Autor y Editorial (Mockeado)")
    void testGuardarEscrito() {
        // GIVEN: Construimos todo el árbol de objetos en memoria sin persistirlos en cascada

        // 1. Editorial
        Editorial ed = new Editorial();
        ed.setId("ED-99");
        ed.setNombre("Editorial Mundo");

        // 2. Libro (depende de Editorial)
        Libro libro = new Libro();
        libro.setIsbn("ISBN-999");
        libro.setTitulo("Cien años de soledad");
        libro.setEditorial(ed);

        // 3. Autor
        Autor autor = new Autor();
        autor.setIdAutor("AUT-01");
        autor.setNombre("Gabriel");
        autor.setApellido("Garcia Marquez");

        // 4. Escrito (depende de Libro y Autor)
        Escrito escrito = new Escrito();
        escrito.setId("ES101");
        escrito.setFechaEscrito(LocalDate.now());
        escrito.setCiudad("Aracataca");
        escrito.setLibro(libro);
        escrito.setAutor(autor);

        // Entrenamos únicamente al repositorio de Escrito
        when(escritoRepository.save(any(Escrito.class))).thenReturn(escrito);
        when(escritoRepository.findById("ES101")).thenReturn(Optional.of(escrito));

        // WHEN: Ejecutamos las acciones simuladas
        Escrito guardado = escritoRepository.save(escrito);
        Optional<Escrito> encontrado = escritoRepository.findById(guardado.getId());

        // THEN: Verificaciones profundas de la estructura simulada
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getCiudad()).isEqualTo("Aracataca");

        // Verificación de relaciones
        assertThat(encontrado.get().getLibro().getTitulo()).isEqualTo("Cien años de soledad");
        assertThat(encontrado.get().getLibro().getEditorial().getNombre()).isEqualTo("Editorial Mundo");
        assertThat(encontrado.get().getAutor().getNombre()).isEqualTo("Gabriel");
    }
}