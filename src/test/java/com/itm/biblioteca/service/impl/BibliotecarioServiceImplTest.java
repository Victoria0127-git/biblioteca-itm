package com.itm.biblioteca.service.impl;

import com.itm.biblioteca.model.Bibliotecario;
import com.itm.biblioteca.repository.BibliotecarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BibliotecarioServiceImplTest {
    @Mock
    private BibliotecarioRepository bibliotecarioRepository; //Crear repo falso

    @InjectMocks
    private BibliotecarioServiceImpl bibliotecarioService;

    @Test
    void listarTodos_RetornaListaBibliotecarios() {
        //GIVEN
        List<Bibliotecario> bibliotecarios = List.of(
                Bibliotecario.builder()
                        .idBibliotecario("B001")
                        .nombre("Julio")
                        .apellido("Jaramillo")
                        .build(),
                Bibliotecario.builder()
                        .idBibliotecario("B002")
                        .nombre("Julia")
                        .apellido("Gomez")
                        .build()
        );

        //WHEN
        when(bibliotecarioRepository.findAll()).thenReturn(bibliotecarios);
        List<Bibliotecario> resultado = bibliotecarioService.listarTodos();

        //THEN
        assertNotNull(resultado);
        assertEquals(bibliotecarios.size(), resultado.size());
        assertEquals(bibliotecarios.get(0), resultado.get(0));

        verify(bibliotecarioRepository, times(1)).findAll();
    }

    @Test
    void buscarPorId_RetornaBibliotecarioBuscado(){
        //GIVEN
        String IdBuscado = "B003";
        Bibliotecario biblioBuscado = Bibliotecario.builder()
                .idBibliotecario(IdBuscado)
                .nombre("Cesar")
                .apellido("Cardenas")
                .build();

        //WHEN
        when(bibliotecarioRepository.findById(IdBuscado)).thenReturn(Optional.of(biblioBuscado));
        Bibliotecario resultado = bibliotecarioService.buscarPorId(IdBuscado).orElse(null);

        //THEN
        assertNotNull(resultado);
        assertEquals(IdBuscado, resultado.getIdBibliotecario());
        verify(bibliotecarioRepository, times(1)).findById(IdBuscado);
    }

    @Test
    void buscarPorId_RetornaBibliotecarioVacio(){
        String IdBuscado = "B003";

        when(bibliotecarioRepository.findById(IdBuscado)).thenReturn(Optional.empty());
        Optional<Bibliotecario> resultado =  bibliotecarioService.buscarPorId(IdBuscado);

        assertTrue(resultado.isEmpty());
        verify(bibliotecarioRepository, times(1)).findById(IdBuscado);
    }

    @Test
    void crearBibliotecario_RetornaBiblioGuardado(){
        Bibliotecario biblioNuevo = Bibliotecario.builder()
                .idBibliotecario("B000")
                .nombre("Carlos")
                .apellido("Rodriguez")
                .build();

        when(bibliotecarioRepository.existsById("B000")).thenReturn(false);
        when(bibliotecarioRepository.save(any(Bibliotecario.class))).thenReturn(biblioNuevo);

        Bibliotecario result = bibliotecarioService.crear(biblioNuevo);

        assertNotNull(result);
        assertEquals(result.getIdBibliotecario(), biblioNuevo.getIdBibliotecario());
        verify(bibliotecarioRepository, times(1)).save(any(Bibliotecario.class));
    }

    @Test
    void actualizarPorId_RetornaBibliotecarioActualizado(){
        String idActualizar = "B100";
        Bibliotecario biblioNuevosDatos = Bibliotecario.builder()
                .apellido("Ochoa")
                .build();

        Bibliotecario biblioEnDB = Bibliotecario.builder()
                .idBibliotecario(idActualizar)
                .nombre("Paulina")
                .apellido("Rojas")
                .build();

        when(bibliotecarioRepository.findById(idActualizar)).thenReturn(Optional.of(biblioEnDB));
        when(bibliotecarioRepository.save(any(Bibliotecario.class))).thenAnswer(i -> i.getArgument(0));

        Bibliotecario result = bibliotecarioService.actualizar(idActualizar, biblioNuevosDatos);

        assertNotNull(result);
        assertEquals(biblioEnDB.getIdBibliotecario(), result.getIdBibliotecario());
        assertEquals(biblioEnDB.getApellido(), result.getApellido());
        assertEquals(biblioEnDB.getNombre(), result.getNombre());

        verify(bibliotecarioRepository, times(1)).findById(idActualizar);
        verify(bibliotecarioRepository, times(1)).save(any(Bibliotecario.class));
    }

    @Test
    void eliminarBibliotecario_RetornaNoContent(){
        String idDelete = "B100";

        when(bibliotecarioRepository.existsById(idDelete)).thenReturn(true);
        doNothing().when(bibliotecarioRepository).deleteById(idDelete);

        bibliotecarioService.eliminar(idDelete);

        verify(bibliotecarioRepository, times(1)).deleteById(idDelete);
    }

    @Test
    void eliminarBibliotecarioInexistente_RetornaError(){
        String idNotExist = "B200";

        when(bibliotecarioRepository.existsById(idNotExist)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> bibliotecarioService.eliminar(idNotExist));

        verify(bibliotecarioRepository, never()).deleteById(anyString());
        verify(bibliotecarioRepository).existsById(idNotExist);
    }
}

