package com.itm.biblioteca.service.impl;

import com.itm.biblioteca.model.Ejemplar;
import com.itm.biblioteca.model.Libro;
import com.itm.biblioteca.repository.EjemplarRepository;
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
class EjemplarServiceImplTest {

    @Mock
    private EjemplarRepository ejemplarRepository;

    @InjectMocks
    private EjemplarServiceImpl ejemplarService;

    @Test
    @DisplayName("Debe listar todos los ejemplares")
    void listarTodos_RetornaListaDeEjemplares() {
        // GIVEN
        Ejemplar ej = new Ejemplar();
        ej.setId("EJ-001");
        when(ejemplarRepository.findAll()).thenReturn(List.of(ej));

        // WHEN
        List<Ejemplar> resultado = ejemplarService.listarTodos();

        // THEN
        assertThat(resultado).hasSize(1);
        verify(ejemplarRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe buscar un ejemplar por ID")
    void buscarPorId_RetornaEjemplarSiExiste() {
        // GIVEN
        String id = "EJ-001";
        Ejemplar ejemplar = new Ejemplar();
        ejemplar.setId(id);
        ejemplar.setUbicacion("Estante A-1");
        when(ejemplarRepository.findById(id)).thenReturn(Optional.of(ejemplar));

        // WHEN
        Optional<Ejemplar> encontrado = ejemplarService.buscarPorId(id);

        // THEN
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getUbicacion()).isEqualTo("Estante A-1");
    }

    @Test
    @DisplayName("Debe crear un ejemplar asociado a un libro")
    void crear_RetornaEjemplarGuardado() {
        // GIVEN
        Libro libro = new Libro();
        libro.setIsbn("1234567890");

        Ejemplar ej = new Ejemplar();
        ej.setId("EJ-001");
        ej.setLibro(libro);
        ej.setEstado(true);

        when(ejemplarRepository.existsById("EJ-001")).thenReturn(false);
        when(ejemplarRepository.save(any(Ejemplar.class))).thenReturn(ej);

        // WHEN
        Ejemplar creado = ejemplarService.crear(ej);

        // THEN
        assertThat(creado).isNotNull();
        assertThat(creado.getId()).isEqualTo("EJ-001");
        assertThat(creado.getLibro()).isNotNull();
        verify(ejemplarRepository, times(1)).save(any(Ejemplar.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción si se intenta crear un ejemplar sin libro")
    void crear_FallaSiNoTieneLibro() {
        // GIVEN
        Ejemplar ejSinLibro = new Ejemplar();
        ejSinLibro.setId("EJ-002");
        ejSinLibro.setLibro(null); // Caso de error

        // WHEN & THEN
        RuntimeException ex = assertThrows(RuntimeException.class, () -> ejemplarService.crear(ejSinLibro));

        assertThat(ex.getMessage()).isEqualTo("Un ejemplar debe estar asociado a un Libro (ISBN).");
        verify(ejemplarRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe actualizar la ubicación y estado de un ejemplar")
    void actualizar_RetornaEjemplarModificado() {
        // GIVEN
        String id = "EJ-001";
        Ejemplar datosNuevos = new Ejemplar();
        datosNuevos.setId(id);
        datosNuevos.setUbicacion("Bodega Central");
        datosNuevos.setEstado(false);

        Ejemplar ejExistente = new Ejemplar();
        ejExistente.setId(id);
        ejExistente.setUbicacion("Estante A-1");
        ejExistente.setEstado(true);

        when(ejemplarRepository.findById(id)).thenReturn(Optional.of(ejExistente));
        when(ejemplarRepository.save(any(Ejemplar.class))).thenAnswer(i -> i.getArguments()[0]);

        // WHEN
        Ejemplar resultado = ejemplarService.actualizar(id, datosNuevos);

        // THEN
        assertThat(resultado.getUbicacion()).isEqualTo("Bodega Central");
        assertThat(resultado.getEstado()).isFalse();
        verify(ejemplarRepository).save(any(Ejemplar.class));
    }

    @Test
    @DisplayName("Debe eliminar ejemplar si existe en la base de datos")
    void eliminar_LlamaAlRepositorioSiExiste() {
        // GIVEN
        String id = "EJ-001";
        when(ejemplarRepository.existsById(id)).thenReturn(true);
        doNothing().when(ejemplarRepository).deleteById(id);

        // WHEN
        ejemplarService.eliminar(id);

        // THEN
        verify(ejemplarRepository, times(1)).deleteById(id);
    }
}