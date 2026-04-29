package com.itm.biblioteca.service.impl;

import com.itm.biblioteca.model.Autor;
import com.itm.biblioteca.repository.AutorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AutorServiceImplTest {

    @Mock
    private AutorRepository autorRepository; //Creamos repo falso

    @InjectMocks
    private AutorServiceImpl autorService; //Inyectamos el repo mock aquí

    @Test
    void listarTodos_RetornaTodosAutores() {
        List<Autor> autores = autorRepository.findAll();
    }

    @Test
    void buscarPorId_RetornaAutorPorId() {
        Autor autor = autorRepository.findById("M001").orElse(null);
    }

    @Test
    void crearAutor_RetornaAutorGuardado() {
        //Crear autor de prueba
        Autor miAutor = Autor.builder()
                .idAutor("A001")
                .nombre("Gabriel")
                .apellido("Garcia")
                .nacionalidad("colombiana")
                .build();
        when(autorRepository.existsById("A001")).thenReturn(false); //Si pregunta la existenicia, que devuelva false
        when(autorRepository.save(any(Autor.class))).thenReturn(miAutor); //Si guarda, que muestre el miAutor que creamos arriba

        //WHEN
        Autor resultado = autorService.crear(miAutor);

        //THEN
        assertNotNull(resultado); //Asegurar que el resultado no sea nulo
        assertEquals(resultado.getIdAutor(), miAutor.getIdAutor()); //Asegurar que guarde correctamente
        verify(autorRepository, times(1)).save(any(Autor.class)); //Comprobar que el servicio llamó el repositorio una vez

    }

    @Test
    void crearAutor_ConExcepcionPorCampoVacio() {
        //Creación de autor de prueba
        Autor autorInvalido = Autor.builder()
                .idAutor("A002")
                .nombre("")
                .apellido("Cervantes")
                .nacionalidad("española")
                .build();

        //Fallar antes de preguntar al repositorio
        RuntimeException ex =  assertThrows(RuntimeException.class, () -> autorService.crear(autorInvalido));
        assertEquals("El nombre del autor es obligatorio.", ex.getMessage());

        verify(autorRepository, never()).save(any()); //Verficar que no se guardó
    }
}
