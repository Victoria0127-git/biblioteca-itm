package com.itm.biblioteca.repository;

import com.itm.biblioteca.model.Bibliotecario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BibliotecarioRepositoryTest {

    @Autowired
    private BibliotecarioRepository bibliotecarioRepository;

    @Test
    void testGuardarBibliotecario() {
        Bibliotecario biblio = new Bibliotecario();
        biblio.setIdBibliotecario("B001");
        biblio.setNombre("Rodrigo");
        biblio.setApellido("Pelaez");

        Bibliotecario guardado = bibliotecarioRepository.save(biblio);

        assertNotNull(guardado);
        assertEquals("B001", guardado.getIdBibliotecario());
        assertEquals("Rodrigo", guardado.getNombre());
    }
}