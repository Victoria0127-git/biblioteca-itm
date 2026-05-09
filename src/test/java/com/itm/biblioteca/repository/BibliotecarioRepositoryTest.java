package com.itm.biblioteca.repository;

import com.itm.biblioteca.model.Bibliotecario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test") // Asegura que use application-test.properties o H2
class BibliotecarioRepositoryTest {

    @Autowired
    private BibliotecarioRepository bibliotecarioRepository;

    @Test
    @DisplayName("Debe persistir un bibliotecario correctamente en la base de datos")
    void testGuardarBibliotecario() {
        // GIVEN: Preparamos el objeto (usando setters o Builder si lo tienes)
        Bibliotecario biblio = new Bibliotecario();
        biblio.setIdBibliotecario("B001");
        biblio.setNombre("Rodrigo");
        biblio.setApellido("Pelaez");

        // WHEN: Ejecutamos la acción de guardar
        Bibliotecario guardado = bibliotecarioRepository.save(biblio);

        // THEN: Verificamos los resultados
        Optional<Bibliotecario> encontrado = bibliotecarioRepository.findById(guardado.getIdBibliotecario());

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getNombre()).isEqualTo("Rodrigo");
        assertThat(encontrado.get().getApellido()).isEqualTo("Pelaez");
        assertThat(encontrado.get().getIdBibliotecario()).isEqualTo("B001");
    }
}