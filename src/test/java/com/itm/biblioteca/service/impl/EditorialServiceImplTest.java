package com.itm.biblioteca.service.impl;

import com.itm.biblioteca.model.Editorial;
import com.itm.biblioteca.repository.EditorialRepository;
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
class EditorialServiceImplTest {

    @Mock
    private EditorialRepository editorialRepository;

    @InjectMocks
    private EditorialServiceImpl editorialService;

    private Editorial editorial;

    @BeforeEach
    void setUp() {
        editorial = new Editorial();
        editorial.setId("ED-001");
        editorial.setNombre("Editorial ITM");
        editorial.setDireccion("Calle 73 # 76A - 354");
        editorial.setTelefono("1234567"); // 7 dígitos para que pase tu validación
    }

    @Test
    void listarTodos() {
        when(editorialRepository.findAll()).thenReturn(Arrays.asList(editorial));

        List<Editorial> resultado = editorialService.listarTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(editorialRepository).findAll();
    }

    @Test
    void buscarPorId() {
        when(editorialRepository.findById("ED-001")).thenReturn(Optional.of(editorial));

        Optional<Editorial> encontrada = editorialService.buscarPorId("ED-001");

        assertTrue(encontrada.isPresent());
        assertEquals("Editorial ITM", encontrada.get().getNombre());
    }

    @Test
    void crear() {
        when(editorialRepository.existsById("ED-001")).thenReturn(false);
        when(editorialRepository.save(any(Editorial.class))).thenReturn(editorial);

        Editorial creada = editorialService.crear(editorial);

        assertNotNull(creada);
        assertEquals("ED-001", creada.getId());
        verify(editorialRepository).save(any(Editorial.class));
    }

    @Test
    void crearFallaPorTelefonoCorto() {

        editorial.setTelefono("123");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            editorialService.crear(editorial);
        });

        assertEquals("El teléfono debe tener al menos 7 dígitos.", exception.getMessage());
    }

    @Test
    void actualizar() {
        when(editorialRepository.findById("ED-001")).thenReturn(Optional.of(editorial));
        when(editorialRepository.save(any(Editorial.class))).thenReturn(editorial);

        Editorial resultado = editorialService.actualizar("ED-001", editorial);

        assertNotNull(resultado);
        verify(editorialRepository).save(any(Editorial.class));
    }

    @Test
    void eliminar() {
        when(editorialRepository.existsById("ED-001")).thenReturn(true);
        doNothing().when(editorialRepository).deleteById("ED-001");

        editorialService.eliminar("ED-001");

        verify(editorialRepository).deleteById("ED-001");
    }
}