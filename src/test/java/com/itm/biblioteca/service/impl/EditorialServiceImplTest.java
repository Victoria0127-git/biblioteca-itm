package com.itm.biblioteca.service.impl;

import com.itm.biblioteca.model.Editorial;
import com.itm.biblioteca.repository.EditorialRepository;
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
class EditorialServiceImplTest {

    @Mock
    private EditorialRepository editorialRepository;

    @InjectMocks
    private EditorialServiceImpl editorialService;

    @Test
    @DisplayName("Debe listar todas las editoriales")
    void listarTodos_RetornaListaDeEditoriales() {
        // GIVEN
        Editorial ed = new Editorial();
        ed.setId("ED-001");
        ed.setNombre("Editorial ITM");
        when(editorialRepository.findAll()).thenReturn(List.of(ed));

        // WHEN
        List<Editorial> resultado = editorialService.listarTodos();

        // THEN
        assertThat(resultado).isNotEmpty();
        assertThat(resultado.size()).isEqualTo(1);
        verify(editorialRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe buscar una editorial por su ID")
    void buscarPorId_RetornaEditorialExistente() {
        // GIVEN
        String id = "ED-001";
        Editorial editorial = new Editorial();
        editorial.setId(id);
        editorial.setNombre("Editorial ITM");
        when(editorialRepository.findById(id)).thenReturn(Optional.of(editorial));

        // WHEN
        Optional<Editorial> encontrada = editorialService.buscarPorId(id);

        // THEN
        assertTrue(encontrada.isPresent());
        assertThat(encontrada.get().getNombre()).isEqualTo("Editorial ITM");
        verify(editorialRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Debe crear una editorial correctamente")
    void crear_RetornaEditorialGuardada() {
        // GIVEN
        Editorial nueva = new Editorial();
        nueva.setId("ED-001");
        nueva.setNombre("Editorial ITM");
        nueva.setTelefono("1234567"); // 7 dígitos

        when(editorialRepository.existsById("ED-001")).thenReturn(false);
        when(editorialRepository.save(any(Editorial.class))).thenReturn(nueva);

        // WHEN
        Editorial creada = editorialService.crear(nueva);

        // THEN
        assertNotNull(creada);
        assertThat(creada.getId()).isEqualTo("ED-001");
        verify(editorialRepository, times(1)).save(any(Editorial.class));
    }

    @Test
    @DisplayName("Debe actualizar los datos de una editorial existente")
    void actualizar_RetornaEditorialActualizada() {
        // GIVEN
        String id = "ED-001";
        Editorial datosNuevos = new Editorial();
        datosNuevos.setNombre("ITM Universitario");

        Editorial editorialExistente = new Editorial();
        editorialExistente.setId(id);
        editorialExistente.setNombre("Editorial ITM");
        editorialExistente.setDireccion("Calle 73");

        when(editorialRepository.findById(id)).thenReturn(Optional.of(editorialExistente));
        when(editorialRepository.save(any(Editorial.class))).thenAnswer(i -> i.getArguments()[0]);

        // WHEN
        Editorial resultado = editorialService.actualizar(id, datosNuevos);

        // THEN
        assertThat(resultado.getNombre()).isEqualTo("ITM Universitario");
        assertThat(resultado.getDireccion()).isEqualTo("Calle 73"); // Se mantiene igual
        verify(editorialRepository).save(any(Editorial.class));
    }

    @Test
    @DisplayName("Debe eliminar una editorial si existe")
    void eliminar_LlamaAlRepositorioSiExiste() {
        // GIVEN
        String id = "ED-001";
        when(editorialRepository.existsById(id)).thenReturn(true);
        doNothing().when(editorialRepository).deleteById(id);

        // WHEN
        editorialService.eliminar(id);

        // THEN
        verify(editorialRepository, times(1)).deleteById(id);
    }
}