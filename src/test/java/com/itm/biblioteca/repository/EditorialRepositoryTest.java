package com.itm.biblioteca.repository;

import com.itm.biblioteca.model.Editorial;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class EditorialRepositoryTest {

    @Autowired
    private EditorialRepository editorialRepository;

    @Test
    @DisplayName("Debe guardar y encontrar una editorial por su ID")
    void testGuardarYBuscarEditorial() {
        // GIVEN: Preparación del objeto
        // Nota: Si usas @Builder en tu modelo, cámbialo por Editorial.builder()...
        Editorial editorial = new Editorial();
        editorial.setId("ED-99");
        editorial.setNombre("Editorial de Prueba");

        // WHEN: Acción de persistir
        Editorial guardada = editorialRepository.save(editorial);

        // THEN: Verificación de resultados
        Optional<Editorial> encontrada = editorialRepository.findById(guardada.getId());

        assertThat(encontrada).isPresent();
        assertThat(encontrada.get().getNombre()).isEqualTo("Editorial de Prueba");
        assertThat(encontrada.get().getId()).isEqualTo("ED-99");
    }
}