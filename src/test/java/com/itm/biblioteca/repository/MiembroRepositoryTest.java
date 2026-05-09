package com.itm.biblioteca.repository;

import com.itm.biblioteca.model.Miembro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MiembroRepositoryTest {

    @Autowired
    private MiembroRepository miembroRepository;

    @Test
    void testGuardarYBuscarMiembro() {

        Miembro miembro = new Miembro();
        miembro.setIdMiembro("M123");
        miembro.setNombre("Salome");
        miembro.setApellido("Botero");
        miembro.setCorreo("salome@correo.com");
        miembro.setDireccion("Calle Falsa 123");
        miembro.setTelefono("3005554433");


        Miembro guardado = miembroRepository.save(miembro);


        assertNotNull(guardado);
        assertEquals("M123", guardado.getIdMiembro());
        assertEquals("Salome", guardado.getNombre());

        Miembro encontrado = miembroRepository.findById("M123").orElse(null);
        assertNotNull(encontrado);
    }
}