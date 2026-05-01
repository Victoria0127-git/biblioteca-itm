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
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AutorServiceImplTest {

    @Mock
    private AutorRepository autorRepository; //Creamos repo falso

    @InjectMocks
    private AutorServiceImpl autorService; //Inyectamos el repo mock aquí

    @Test
    void listarTodos_RetornaTodosAutores() {
        // GIVEN: Preparamos los datos que devolverá el mock
        Autor autor1 = Autor.builder()
                .idAutor("A001")
                .nombre("Gabriel")
                .apellido("Garcia")
                .nacionalidad("Colombiana")
                .build();
        Autor autor2 = Autor.builder()
                .idAutor("A002")
                .nombre("Isabel")
                .apellido("Parra")
                .nacionalidad("Santa Catarina")
                .build();
        when(autorRepository.findAll()).thenReturn(List.of(autor1, autor2));

        // WHEN
        List<Autor> autores = autorService.listarTodos();

        // THEN: Verificamos resultados
        assertNotNull(autores);
        assertEquals(2, autores.size());
        verify(autorRepository, times(1)).findAll();
    }

    @Test
    void buscarPorId_RetornaAutorPorId() {
        // GIVEN: Preparamos el objeto que simulará estar en la DB
        String idBuscado = "A003";
        Autor autorSimulado = Autor.builder()
                .idAutor(idBuscado)
                .nombre("Mario")
                .apellido("Gomez")
                .nacionalidad("Chilena")
                .build();

        // Configuramos el mock para que devuelva el autor envuelto en un Optional
        when(autorRepository.findById(idBuscado)).thenReturn(Optional.of(autorSimulado));

        // WHEN
        Autor resultado = autorService.buscarPorId(idBuscado).orElse(null); // Asumiendo que se llama así

        // THEN: Verificaciones
        assertNotNull(resultado);
        assertEquals(idBuscado, resultado.getIdAutor());
        assertEquals("Mario", resultado.getNombre());
        verify(autorRepository, times(1)).findById(idBuscado);
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

    @Test
    void actualizarNombre_RetornaAutorActualizado(){
        // 1. Datos de entrada (lo que envía el usuario)
        String idBuscado = "A004";
        Autor autorConNuevosDatos = Autor.builder()
                .nombre("Stephen")
                .build();

        // 2. Mock de lo que está actualmente en la base de datos
        Autor autorEnBaseDatos = Autor.builder()
                .idAutor("A004")
                .nombre("Steven") // Nombre viejo
                .apellido("King")
                .nacionalidad("Estadounidense")
                .build();

        // Configuración de los Mocks
        when(autorRepository.findById(idBuscado)).thenReturn(Optional.of(autorEnBaseDatos));
        // El save devuelve el mismo objeto que recibe (el argumento 0)
        when(autorRepository.save(any(Autor.class))).thenAnswer(i -> i.getArguments()[0]);

        // 3. Ejecución
        Autor resultado = autorService.actualizar(idBuscado, autorConNuevosDatos);

        // 4. Verificaciones (Assertions)
        assertNotNull(resultado, "El autor actualizado no debería ser nulo");
        assertEquals("Stephen", resultado.getNombre(), "El nombre debería haber cambiado a Stephen");
        assertEquals("King", resultado.getApellido(), "El apellido debería mantenerse igual");

        // Verificamos que se llamó a los métodos del repositorio correctamente
        verify(autorRepository, times(1)).findById(idBuscado);
        verify(autorRepository, times(1)).save(any(Autor.class));
    }
}
