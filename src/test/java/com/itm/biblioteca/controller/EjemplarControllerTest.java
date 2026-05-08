package com.itm.biblioteca.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itm.biblioteca.model.Ejemplar;
import com.itm.biblioteca.model.Libro;
import com.itm.biblioteca.service.IEjemplarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(EjemplarController.class)
public class EjemplarControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IEjemplarService ejemplarService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void obtenerTodosEjemplares_RetornaListaEjemplares() throws Exception {
        List<Ejemplar> ejemplaresList = List.of(
                Ejemplar.builder()
                        .id("EJ10")
                        .ubicacion("CJX012")
                        .estado(true)
                        .libro(Libro.builder().isbn("9783161484100").build())
                        .build(),
                Ejemplar.builder()
                        .id("EJ11")
                        .ubicacion("EFG123")
                        .estado(false)
                        .libro(Libro.builder().isbn("9781331331315").build())
                        .build()
        );

        when(ejemplarService.listarTodos() ).thenReturn(ejemplaresList);

        mockMvc.perform(get("/api/ejemplares")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(ejemplaresList.size()))
                .andExpect(jsonPath("$[0].id").value("EJ10"))
                .andExpect(jsonPath("$[1].ubicacion").value("EFG123"));
    }

    @Test
    void crearEjemplar_RetornaStatusIsCreated() throws Exception {
        Ejemplar ejemplar = Ejemplar.builder()
                .id("EJ00")
                .ubicacion("CZE012")
                .estado(true)
                .libro(Libro.builder().isbn("1234567891234").build())
                .build();

        when(ejemplarService.crear(any(Ejemplar.class))).thenReturn(ejemplar);

        mockMvc.perform(post("/api/ejemplares")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(ejemplar)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("EJ00"));
    }

    @Test
    void actualizarEjemplar_RetornaStatusIsOk() throws Exception {
        String idActualizar = "EJ00";
        Ejemplar datosNuevos = Ejemplar.builder()
                .estado(false)
                .build();

        Ejemplar ejemplarActualizado = Ejemplar.builder()
                .id(idActualizar)
                .ubicacion("CZE012")
                .estado(false)
                .libro(Libro.builder().isbn("1234987612390").build())
                .build();

        when(ejemplarService.actualizar(eq(idActualizar), any(Ejemplar.class))).thenReturn(ejemplarActualizado);

        mockMvc.perform(put("/api/ejemplares"+idActualizar)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(datosNuevos)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("EJ00"));
    }

    @Test
    void eliminarEjemplar_RetornaStatusIsNoContent() throws Exception {
        String idEliminar = "EJ999";
        doNothing().when(ejemplarService).eliminar(eq(idEliminar));

        mockMvc.perform(delete("/api/ejemplares"+idEliminar))
                .andExpect(status().isNoContent());

        verify(ejemplarService, times(1)).eliminar(eq(idEliminar));
    }
}
