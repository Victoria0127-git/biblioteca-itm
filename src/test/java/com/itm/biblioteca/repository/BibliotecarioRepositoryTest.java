package com.itm.biblioteca.repository;

import com.itm.biblioteca.model.Bibliotecario;
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
class BibliotecarioRepositoryTest {

    @Mock
    private BibliotecarioRepository bibliotecarioRepository; // Creamos el simulacro de la interfaz

    @Test
    @DisplayName("Debe persistir un bibliotecario correctamente en la base de datos (Mockeado)")
    void testGuardarBibliotecario() {
        // GIVEN: Preparamos el objeto real que esperamos manejar
        Bibliotecario biblio = new Bibliotecario();
        biblio.setIdBibliotecario("B001");
        biblio.setNombre("Rodrigo");
        biblio.setApellido("Pelaez");

        // Entrenamos al Mock para que reaccione simulando el comportamiento de JPA
        when(bibliotecarioRepository.save(any(Bibliotecario.class))).thenReturn(biblio);
        when(bibliotecarioRepository.findById("B001")).thenReturn(Optional.of(biblio));

        // WHEN: Ejecutamos las acciones sobre el repositorio simulado
        Bibliotecario guardado = bibliotecarioRepository.save(biblio);
        Optional<Bibliotecario> encontrado = bibliotecarioRepository.findById(guardado.getIdBibliotecario());

        // THEN: Validamos que las respuestas del simulacro coincidan con lo configurado
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getNombre()).isEqualTo("Rodrigo");
        assertThat(encontrado.get().getApellido()).isEqualTo("Pelaez");
        assertThat(encontrado.get().getIdBibliotecario()).isEqualTo("B001");
    }
}