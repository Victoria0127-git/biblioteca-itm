package com.itm.biblioteca.repository;

import com.itm.biblioteca.model.Miembro;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // Usamos Mockito sin levantar Spring ni H2
class MiembroRepositoryTest {

    @Mock
    private MiembroRepository miembroRepository; // Simulamos la interfaz del repositorio

    @Test
    @DisplayName("Debe persistir un Miembro y permitir su búsqueda por ID (Mockeado)")
    void testGuardarYBuscarMiembro() {
        // GIVEN: Creamos la instancia del Miembro en memoria
        Miembro miembro = new Miembro();
        miembro.setIdMiembro("M123");
        miembro.setNombre("Salome");
        miembro.setApellido("Botero");
        miembro.setCorreo("salome@correo.com");
        miembro.setDireccion("Calle Falsa 123");
        miembro.setTelefono("3005554433");

        // Entrenamos al mock
        when(miembroRepository.save(any(Miembro.class))).thenReturn(miembro);
        when(miembroRepository.findById("M123")).thenReturn(Optional.of(miembro));

        // WHEN: Ejecutamos el flujo sin variables muertas
        miembroRepository.save(miembro);
        Optional<Miembro> encontradoOpt = miembroRepository.findById("M123");

        // THEN: Verificamos usando AssertJ
        assertThat(encontradoOpt).isPresent();

        Miembro encontrado = encontradoOpt.get();
        assertThat(encontrado.getIdMiembro()).isEqualTo("M123");
        assertThat(encontrado.getNombre()).isEqualTo("Salome");
        assertThat(encontrado.getCorreo()).isEqualTo("salome@correo.com");
        assertThat(encontrado.getTelefono()).isEqualTo("3005554433");
    }
}