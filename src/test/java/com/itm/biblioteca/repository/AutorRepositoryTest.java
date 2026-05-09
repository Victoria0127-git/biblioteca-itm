package com.itm.biblioteca.repository;

import com.itm.biblioteca.model.Autor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat; // Para el assertThat
import java.util.Optional;

@ActiveProfiles("test")
@DataJpaTest
class AutorRepositoryTest {

    @Autowired
    private AutorRepository autorRepository;

    @Test
    void guardarAutor_debePersistirenDB() {
        // GIVEN: creamos objetos usando tu Builder
        Autor autor = Autor.builder()
                .idAutor("A001")
                .nombre("Edgar Allan")
                .apellido("Poe")
                .nacionalidad("Estadounidense")
                .build();

        // WHEN: guardar en DB de prueba (H2 por defecto)
        Autor guardado = autorRepository.save(autor);

        // THEN: verificar que realmente se guardó
        Optional<Autor> encontrado = autorRepository.findById(guardado.getIdAutor());

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getNombre()).isEqualTo("Edgar Allan");
    }
}