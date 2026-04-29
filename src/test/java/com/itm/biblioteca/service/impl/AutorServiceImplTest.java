package com.itm.biblioteca.service.impl;

import com.itm.biblioteca.model.Autor;
import com.itm.biblioteca.repository.AutorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
        Autor autorEntrada = new Autor();


    }
}
