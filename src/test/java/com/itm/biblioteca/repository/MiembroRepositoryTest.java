package com.itm.biblioteca.repository;

import com.itm.biblioteca.model.Miembro;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class MiembroRepositoryTest {

    @Autowired
    private MiembroRepository miembroRepository;

    @Test
    @DisplayName("Debe persistir un Miembro y permitir su búsqueda por ID")
    void testGuardarYBuscarMiembro() {
        // GIVEN: Creamos la instancia del Miembro
        Miembro miembro = new Miembro();
        miembro.setIdMiembro("M123");
        miembro.setNombre("Salome");
        miembro.setApellido("Botero");
        miembro.setCorreo("salome@correo.com");
        miembro.setDireccion("Calle Falsa 123");
        miembro.setTelefono("3005554433");

        // WHEN: Guardamos en el repositorio
        Miembro guardado = miembroRepository.save(miembro);

        // THEN: Verificamos usando AssertJ
        Optional<Miembro> encontradoOpt = miembroRepository.findById(guardado.getIdMiembro());

        assertThat(encontradoOpt).isPresent();

        Miembro encontrado = encontradoOpt.get();
        assertThat(encontrado.getIdMiembro()).isEqualTo("M123");
        assertThat(encontrado.getNombre()).isEqualTo("Salome");
        assertThat(encontrado.getCorreo()).isEqualTo("salome@correo.com");
        assertThat(encontrado.getTelefono()).isEqualTo("3005554433");
    }
}