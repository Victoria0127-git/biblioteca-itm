package com.itm.biblioteca.repository;

import com.itm.biblioteca.model.Autor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // 1. Usamos el motor de Mockito puro, cero Spring/H2
class AutorRepositoryTest {

    @Mock
    private AutorRepository autorRepository; // 2. Creamos un Mock (simulacro) de la interfaz

    @Test
    void guardarAutor_debePersistirenDB() {
        // GIVEN: Preparamos el objeto de prueba
        Autor autor = Autor.builder()
                .idAutor("A001")
                .nombre("Edgar Allan")
                .apellido("Poe")
                .nacionalidad("Estadounidense")
                .build();

        // 3. Simulamos el comportamiento del repositorio (Mocking)
        // Le decimos a Mockito: "Cuando llamen a save(), devuelve este objeto"
        when(autorRepository.save(any(Autor.class))).thenReturn(autor);

        // Le decimos: "Cuando busquen por el ID 'A001', devuelve el objeto envuelto en un Optional"
        when(autorRepository.findById("A001")).thenReturn(Optional.of(autor));

        // WHEN: Ejecutamos los métodos del repositorio mockeado
        Autor guardado = autorRepository.save(autor);
        Optional<Autor> encontrado = autorRepository.findById(guardado.getIdAutor());

        // THEN: Verificamos que el Mock respondió exactamente lo que programamos
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getNombre()).isEqualTo("Edgar Allan");
        assertThat(encontrado.get().getIdAutor()).isEqualTo("A001");
    }
}