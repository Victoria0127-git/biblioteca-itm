package com.itm.biblioteca.repository;

import com.itm.biblioteca.model.Editorial;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EditorialRepositoryTest {

    @Autowired
    private EditorialRepository editorialRepository;

    @Test
    void testGuardarYBuscarEditorial() {

        Editorial editorial = new Editorial();
        editorial.setId("ED-99"); // Ajusta si el ID es autogenerado
        editorial.setNombre("Editorial de Prueba");


        Editorial guardada = editorialRepository.save(editorial);


        assertNotNull(guardada);
        assertEquals("Editorial de Prueba", guardada.getNombre());


        Editorial encontrada = editorialRepository.findById(guardada.getId()).orElse(null);
        assertNotNull(encontrada);
    }
}