package com.itm.biblioteca.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itm.biblioteca.model.Editorial;
import com.itm.biblioteca.model.Libro;
import com.itm.biblioteca.service.ILibroService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(LibroController.class)
public class LibroControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ILibroService libroService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void listarTodosLibros_RetornaStatusIsOk() throws Exception {
        List<Libro> libros = List.of(
                Libro.builder()
                        .isbn("isbn1")
                        .titulo("titulo1")
                        .numPag(100)
                        .editorial(Editorial.builder().id("editorial1").build())
                        .build(),
                Libro.builder()
                        .isbn("isbn2")
                        .titulo("titulo2")
                        .numPag(200)
                        .editorial(Editorial.builder().id("editorial2").build())
                        .build(),
                Libro.builder()
                        .isbn("isbn3")
                        .titulo("titulo3")
                        .numPag(300)
                        .editorial(Editorial.builder().id("editorial3").build())
                        .build()
        );

        when(libroService.listarTodos()).thenReturn(libros);

        mockMvc.perform(get("/api/libros")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(libros.size()))
                .andExpect(jsonPath("$[0].isbn").value(libros.get(0).getIsbn()))
                .andExpect(jsonPath("$[1].titulo").value(libros.get(1).getTitulo()))
                .andExpect(jsonPath("$[2].numPag").value(libros.get(2).getNumPag()));
    }

    @Test
    void buscarLibroPorId_RetornaNoFound() throws Exception {
        String idNoExistente = "L617";
        when(libroService.buscarPorId(idNoExistente)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/libros/" + idNoExistente)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());

        verify(libroService, times(1)).buscarPorId(idNoExistente);
    }

    @Test
    void crearLibro_RetornaIsCreated() throws Exception {
        Libro libroNuevo = Libro.builder()
                .isbn("isbnTest")
                .titulo("tituloTest")
                .numPag(100)
                .editorial(Editorial.builder().id("editorialTest").build())
                .build();

        when(libroService.crear(any(Libro.class))).thenReturn(libroNuevo);

        mockMvc.perform(post("/api/libros")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(libroNuevo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.isbn").value(libroNuevo.getIsbn()));
    }

    @Test
    void actualizarLibro_RetornaNotFound() throws Exception {
        String isbnNoExistente = "L617";
        Libro libroPrueba = Libro.builder()
                .isbn(isbnNoExistente)
                .titulo("tituloTest")
                .numPag(100)
                .editorial(Editorial.builder().id("editorialTest").build())
                .build();

        when(libroService.actualizar(eq(isbnNoExistente), any(Libro.class))).thenThrow(new RuntimeException());

        mockMvc.perform(put("/api/libros/" + isbnNoExistente)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(libroPrueba)))
                        .andExpect(status().isNotFound());

        verify(libroService, times(1)).actualizar(isbnNoExistente, libroPrueba);
    }

    @Test
    void eliminarLibro_RetornaNoContent() throws Exception {
        String isbnEliminar = "Lt35t";

        doNothing().when(libroService).eliminar(isbnEliminar);

        mockMvc.perform(delete("/api/libros/" + isbnEliminar)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());

        verify(libroService, times(1)).eliminar(isbnEliminar);
    }

}
