package com.itm.biblioteca.service.impl;

import com.itm.biblioteca.model.Miembro;
import com.itm.biblioteca.repositorySQL.MiembroRepositorySQL;
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
class MiembroServiceImplTest {

    @Mock
    private MiembroRepositorySQL miembroRepository;

    @InjectMocks
    private MiembroServiceImpl miembroService;

    @Test
    @DisplayName("Debe listar todos los miembros de la biblioteca")
    void listarTodos_RetornaListaDeMiembros() {
        // GIVEN
        Miembro m = new Miembro();
        m.setNombre("Victoria");
        when(miembroRepository.getMiembros()).thenReturn(List.of(m));

        // WHEN
        List<Miembro> resultado = miembroService.listarTodos();

        // THEN
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNombre()).isEqualTo("Victoria");
        verify(miembroRepository, times(1)).getMiembros();
    }

    @Test
    @DisplayName("Debe crear un miembro correctamente")
    void crear_RetornaMiembroGuardado() {
        // GIVEN
        Miembro miembro = new Miembro();
        miembro.setIdMiembro("123");
        miembro.setNombre("Victoria");
        miembro.setCorreo("victoria@itm.edu.co");

        when(miembroRepository.existsById("123")).thenReturn(false);
        when(miembroRepository.insertarMiembro(any(Miembro.class))).thenReturn(miembro);

        // WHEN
        Miembro creado = miembroService.crear(miembro);

        // THEN
        assertThat(creado).isNotNull();
        assertThat(creado.getNombre()).isEqualTo("Victoria");
        verify(miembroRepository, times(1)).insertarMiembro(any(Miembro.class));
    }

    @Test
    @DisplayName("Debe fallar al crear si el correo no tiene el formato correcto")
    void crear_FallaPorCorreoInvalido() {
        // GIVEN
        Miembro miembroInvalido = new Miembro();
        miembroInvalido.setCorreo("correo-sin-arroba");

        // WHEN & THEN
        RuntimeException ex = assertThrows(RuntimeException.class, () -> miembroService.crear(miembroInvalido));

        assertThat(ex.getMessage()).isEqualTo("El nombre del miembro es obligatorio.");
        verify(miembroRepository, never()).insertarMiembro(any());
    }

    @Test
    @DisplayName("Debe buscar un miembro por su ID")
    void buscarPorId_RetornaMiembroSiExiste() {
        // GIVEN
        String id = "123";
        Miembro miembro = new Miembro();
        miembro.setIdMiembro(id);
        when(miembroRepository.buscarPorId(id)).thenReturn(Optional.of(miembro));

        // WHEN
        Optional<Miembro> encontrado = miembroService.buscarPorId(id);

        // THEN
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getIdMiembro()).isEqualTo("123");
    }

    @Test
    @DisplayName("Debe eliminar un miembro si existe")
    void eliminar_LlamaAlRepositorioSiExiste() {
        // GIVEN
        String id = "123";
        when(miembroRepository.existsById(id)).thenReturn(true);
        doNothing().when(miembroRepository).eliminarMiembro(id);

        // WHEN
        miembroService.eliminar(id);

        // THEN
        verify(miembroRepository, times(1)).eliminarMiembro(id);
    }
}