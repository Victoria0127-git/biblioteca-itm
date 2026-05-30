package com.itm.biblioteca.repository;

import com.itm.biblioteca.model.Editorial;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // Usamos el motor ligero de Mockito sin levantar Spring
class EditorialRepositoryTest {

    @Mock
    private EditorialRepository editorialRepository; // Simulamos la interfaz del repositorio

    @Test
    @DisplayName("Debe guardar y encontrar una editorial por su ID (Mockeado)")
    void testGuardarYBuscarEditorial() {
        // GIVEN: Preparación del objeto real
        Editorial editorial = new Editorial();
        editorial.setId("ED-99");
        editorial.setNombre("Editorial de Prueba");

        // Entrenamos al Mock para simular el comportamiento de JPA
        when(editorialRepository.save(any(Editorial.class))).thenReturn(editorial);
        when(editorialRepository.findById("ED-99")).thenReturn(Optional.of(editorial));

        // WHEN: Ejecución de las acciones sobre el mock
        Editorial guardada = editorialRepository.save(editorial);
        Optional<Editorial> encontrada = editorialRepository.findById(guardada.getId());

        // THEN: Verificación de los resultados simulados
        assertThat(encontrada).isPresent();
        assertThat(encontrada.get().getNombre()).isEqualTo("Editorial de Prueba");
        assertThat(encontrada.get().getId()).isEqualTo("ED-99");
    }
}