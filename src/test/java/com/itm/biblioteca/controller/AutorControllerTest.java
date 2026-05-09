package com.itm.biblioteca.controller;

import com.itm.biblioteca.model.Autor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itm.biblioteca.service.IAutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(AutorController.class)
class AutorControllerTest {
    @Autowired
    private MockMvc mockMvc; // Simula las peticiones http

    @MockitoBean
    private IAutorService autorService; //Simula el servicio

    private final ObjectMapper objectMapper = new ObjectMapper(); //Convertir objetos JAVA a JSON

    @Test
    void crearAutor_DebeRetornarStatusCreated() throws Exception {
        //GIVEN: crear autor
        Autor autor = Autor.builder()
                .idAutor("A000")
                .nombre("Test")
                .apellido("Prueba")
                .nacionalidad("Testland")
                .build();

        when(autorService.crear(any(Autor.class))).thenReturn(autor);

        //WHEN AND THEN
        mockMvc.perform(post("/api/autores")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(autor)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Test"));
    }

    @Test
    void obtenerTodos_RetornaListadeAutores() throws Exception {
        //GIVEN
        List<Autor> autores = List.of(
                Autor.builder()
                        .idAutor("A100")
                        .nombre("Test1")
                        .apellido("Prueba1")
                        .nacionalidad("Testland1")
                        .build(),
                Autor.builder()
                        .idAutor("A200")
                        .nombre("Test2")
                        .apellido("Prueba2")
                        .nacionalidad("Testland2")
                        .build()
        );

        //WHEN
        when(autorService.listarTodos()).thenReturn(autores);

        //THEN
        mockMvc.perform(get("/api/autores")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].idAutor").value("A100"))
                .andExpect(jsonPath("$[1].nombre").value("Test2"));
    }

    @Test
    void buscarPorId_RetornaAutorId() throws Exception {
        //GIVEN
        String idBuscado = "A200";
        Autor autorBuscado = Autor.builder()
                .idAutor(idBuscado)
                .nombre("Test")
                .apellido("Prueba")
                .nacionalidad("Testland")
                .build();

        //WHEN
        when(autorService.buscarPorId(idBuscado)).thenReturn(Optional.of(autorBuscado));

        //THEN
        mockMvc.perform(get("/api/autores/" + idBuscado).
                accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idAutor").value(idBuscado))
                .andExpect(jsonPath("$.nombre").value("Test"));
    }

    @Test
    void buscarPorId_CuandoNoExiste_RetornaNotFound() throws Exception {
        String idInexistente = "999";

        // GIVEN: El servicio devuelve un Optional vacío
        when(autorService.buscarPorId(idInexistente)).thenReturn(Optional.empty());

        // WHEN & THEN: El controlador debe responder 404 Not Found
        mockMvc.perform(get("/api/autores/{id}", idInexistente))
                .andExpect(status().isNotFound());
    }

    @Test
    void actualizarAutor_RetornaOk() throws Exception {
        String idActualizar = "A111";
        Autor datosActualizar = Autor.builder()
                .apellido("Perez")
                .nacionalidad("Pepeganda")
                .build();

        Autor autorActualizado = Autor.builder()
                .idAutor(idActualizar)
                .nombre("Eutimio")
                .apellido("Perez")
                .nacionalidad("Pepeganda")
                .build();

        when(autorService.actualizar(eq(idActualizar), any(Autor.class))).thenReturn(autorActualizado);

        mockMvc.perform(put("/api/autores/"+idActualizar)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(datosActualizar)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idAutor").value(idActualizar))
                .andExpect(jsonPath("$.nombre").value("Eutimio"))
                .andExpect(jsonPath("$.apellido").value("Perez"));
    }

    @Test
    void eliminarAutor_DebeRetornarNoContent() throws Exception {
        doNothing().when(autorService).eliminar("A000");

        mockMvc.perform(delete("/api/autores/A000"))
                .andExpect(status().isNoContent());

        verify(autorService, times(1)).eliminar("A000");
    }
}
