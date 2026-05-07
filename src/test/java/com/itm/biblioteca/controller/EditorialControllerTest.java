package com.itm.biblioteca.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itm.biblioteca.model.Editorial;
import com.itm.biblioteca.service.IEditorialService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(EditorialController.class)
public class EditorialControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IEditorialService editorialService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void crearEditorial_RetornaStatusIsCreated () throws Exception {
        Editorial editorialCreada = Editorial.builder()
                .id("E000")
                .nombre("Prueba")
                .telefono("123456789")
                .direccion("Direccion Prueba")
                .build();

        when(editorialService.crear(any(Editorial.class))).thenReturn(editorialCreada);

        mockMvc.perform(post("/api/editoriales")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(editorialCreada)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Prueba"));
    }

    @Test
    void actualizarEditorial_RetornaStatusIsOk () throws Exception {
        String idActualizar = "E111";

        Editorial datosNuevos = Editorial.builder()
                .nombre("Test")
                .build();

        Editorial editorialActualizada = Editorial.builder()
                .id(idActualizar)
                .nombre("Test")
                .direccion("DireccionTest")
                .telefono("987654321")
                .build();

        when(editorialService.actualizar(eq(idActualizar), any(Editorial.class))).thenReturn(editorialActualizada);

        mockMvc.perform(put("/api/editoriales/"+idActualizar)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(datosNuevos)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Test"));
    }

    @Test
    void eliminarEditorialInExistente_RetornaNotFound () throws Exception {
        String idNoExiste = "E900";
        String mensajeError = "Editorial no encontrada";

        doThrow(new RuntimeException(mensajeError))
                .when(editorialService).eliminar(idNoExiste);

        mockMvc.perform(delete("/api/editoriales/{id}", idNoExiste))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andExpect(content().string(mensajeError));
    }

    @Test
    void buscarPorIdNoExiste_RetornaNotFound () throws Exception {
        String idNoExiste = "E999";

        when(editorialService.buscarPorId(idNoExiste)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/editoriales/{id}", idNoExiste))
                .andExpect(status().isNotFound());
    }
}
